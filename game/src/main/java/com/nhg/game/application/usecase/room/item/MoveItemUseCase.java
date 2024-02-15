package com.nhg.game.application.usecase.room.item;

import com.nhg.common.domain.UseCase;
import com.nhg.game.application.repository.ItemRepository;
import com.nhg.game.application.usecase.room.RoomSharedUseCase;
import com.nhg.game.domain.item.ItemType;
import com.nhg.game.domain.item.RoomItem;
import com.nhg.game.domain.room.Room;
import com.nhg.game.domain.room.entity.Entity;
import com.nhg.game.domain.room.entity.component.ComponentType;
import com.nhg.game.domain.room.entity.component.InteractionComponent;
import com.nhg.game.domain.shared.position.Position2;
import com.nhg.game.domain.shared.position.Position3;
import com.nhg.game.domain.user.User;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class MoveItemUseCase {

    private final RoomSharedUseCase roomSharedUseCase;
    private final ItemRepository itemRepository;


    public void moveItem(@NonNull Entity userEntity, @NonNull Entity itemEntity,
                         @NonNull Room room, @NonNull Position2 position) {
        User user = roomSharedUseCase.getUserFromEntity(userEntity);
        RoomItem item = roomSharedUseCase.getItemFromEntity(itemEntity);

        if (user == null || item == null) return;

        interactionOnMove(itemEntity, userEntity);

        // TODO: heightmap
        float z = room.getRoomLayout().getTile(position).getPosition().getZ();
        Position3 newPosition = new Position3(position, z);

        item.setPosition(newPosition);

        if (item.getPrototype().getItemType() == ItemType.FLOOR_ITEM) {
            roomSharedUseCase.updateRoomTile(room, item);
        }

        itemRepository.save(item);
    }

    private void interactionOnMove(Entity itemEntity, Entity userEntity) {
        InteractionComponent interactionComponent =
                (InteractionComponent) itemEntity.getComponent(ComponentType.Interaction);

        if (interactionComponent == null) return;

        interactionComponent.onMove(userEntity);
    }


}
