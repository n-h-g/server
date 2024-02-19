package com.nhg.game.application.usecase.room.item;

import com.nhg.common.domain.UseCase;
import com.nhg.common.domain.event.DomainEventPublisher;
import com.nhg.game.application.repository.ItemRepository;
import com.nhg.game.domain.item.Item;
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

        RoomItem item = itemToRoomItem(itemOpt.get());

        // if the item can't be placed throws an exception.
        RoomItemUtils.moveItemAtOrThrow(room, item, position);

        // create and set the entity for the item
        Entity itemEntity = Entity.fromItem(item, room, eventPublisher);
        item.setEntity(itemEntity);
        room.addEntity(itemEntity);

        interactionOnPlace(itemEntity, userEntity);

        itemRepository.save(item);

        return itemEntity;
    }

    private RoomItem itemToRoomItem(Item item) {
        return new RoomItem(item.getId(), item.getPrototype(), item.getOwner(),
                            Rotation.NORTH, Position3.Zero, "");
    }

    private void interactionOnPlace(Entity itemEntity, Entity userEntity) {
        InteractionComponent interactionComponent =
                (InteractionComponent) itemEntity.getComponent(ComponentType.Interaction);

        if (interactionComponent == null) return;

        interactionComponent.onPlace(userEntity);
    }
}
