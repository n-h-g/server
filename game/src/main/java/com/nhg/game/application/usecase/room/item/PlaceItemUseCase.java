package com.nhg.game.application.usecase.room.item;

import com.nhg.common.domain.UseCase;
import com.nhg.common.domain.event.DomainEventPublisher;
import com.nhg.game.application.exception.ProblemCode;
import com.nhg.game.application.exception.UseCaseException;
import com.nhg.game.application.repository.ItemRepository;
import com.nhg.game.application.usecase.room.RoomConstants;
import com.nhg.game.domain.item.Item;
import com.nhg.game.domain.item.ItemPrototype;
import com.nhg.game.domain.item.RoomItem;
import com.nhg.game.domain.room.Room;
import com.nhg.game.domain.room.entity.Entity;
import com.nhg.game.domain.room.entity.component.ComponentType;
import com.nhg.game.domain.room.entity.component.InteractionComponent;
import com.nhg.game.domain.shared.position.Position2;
import com.nhg.game.domain.shared.position.Position3;
import com.nhg.game.domain.shared.position.Rotation;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@UseCase
@RequiredArgsConstructor
public class PlaceItemUseCase {

    private final ItemRepository itemRepository;
    private final DomainEventPublisher eventPublisher;

    public Entity placeItem(@NonNull Room room, @NonNull Entity userEntity, int itemId,
                            @NonNull Position2 position) {
        Optional<Item> itemOpt = itemRepository.findItemById(itemId);

        if (itemOpt.isEmpty()) return null;

        Item item = itemOpt.get();

        // TODO: heightmap
        float z = room.getRoomLayout().getTile(position).getPosition().getZ();
        Position3 itemPosition = new Position3(position, z);

        // if the item can't be placed throws an exception.
        canPlaceItemAtOrThrow(room, item.getPrototype(), itemPosition);

        RoomItem roomItem = itemToRoomItem(item, itemPosition);

        Entity itemEntity = Entity.fromItem(roomItem, room, eventPublisher);

        roomItem.setEntity(itemEntity);
        room.addEntity(itemEntity);

        interactionOnPlace(itemEntity, userEntity);

        itemRepository.save(roomItem);

        return itemEntity;
    }

    private void canPlaceItemAtOrThrow(Room room, ItemPrototype itemPrototype, Position3 position) throws UseCaseException {
        if (room.getEntities().itemsCount() == RoomConstants.MAX_ITEMS)
            throw new UseCaseException(ProblemCode.MAX_ITEMS);

        if (position.getZ() > RoomConstants.MAX_HEIGHT)
            throw new UseCaseException(ProblemCode.MAX_HEIGHT);
    }

    private RoomItem itemToRoomItem(Item item, Position3 position) {
        return new RoomItem(item.getId(), item.getPrototype(), item.getOwner(),
                            Rotation.NORTH, position, "");
    }

    private void interactionOnPlace(Entity itemEntity, Entity userEntity) {
        InteractionComponent interactionComponent =
                (InteractionComponent) itemEntity.getComponent(ComponentType.Interaction);

        if (interactionComponent == null) return;

        interactionComponent.onPlace(userEntity);
    }
}
