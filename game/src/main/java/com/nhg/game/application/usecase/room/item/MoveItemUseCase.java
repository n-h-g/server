package com.nhg.game.application.usecase.room.item;

import com.nhg.common.domain.UseCase;
import com.nhg.game.application.repository.ItemRepository;
import com.nhg.game.application.usecase.room.RoomUtils;
import com.nhg.game.application.usecase.room.entity.EntityUtils;
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
    
    private final ItemRepository itemRepository;


    public void moveItem(@NonNull Entity userEntity, @NonNull Entity itemEntity,
                         @NonNull Room room, @NonNull Position2 position) {
        User user = EntityUtils.getUserFromEntity(userEntity);
        RoomItem item = EntityUtils.getItemFromEntity(itemEntity);

        if (user == null || item == null) return;

        interactionOnMove(itemEntity, userEntity);

        setNewPosition(item, room, position);

        if (item.getPrototype().getItemType() == ItemType.FLOOR_ITEM) {
            RoomUtils.updateRoomTile(room, item);
        }
    }

    private void interactionOnMove(Entity itemEntity, Entity userEntity) {
        InteractionComponent interactionComponent =
                (InteractionComponent) itemEntity.getComponent(ComponentType.Interaction);

        if (interactionComponent == null) return;

        interactionComponent.onMove(userEntity);
    }

    private void setNewPosition(RoomItem item, Room room, Position2 position) {
        // TODO: heightmap
        float z = room.getRoomLayout().getTile(position).getPosition().getZ();
        Position3 newPosition = new Position3(position, z);

        item.setPosition(newPosition);

        itemRepository.save(item);
    }


}
