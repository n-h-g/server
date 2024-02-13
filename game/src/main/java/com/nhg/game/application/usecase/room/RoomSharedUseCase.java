package com.nhg.game.application.usecase.room;

import com.nhg.common.domain.UseCase;
import com.nhg.game.domain.item.RoomItem;
import com.nhg.game.domain.room.Room;
import com.nhg.game.domain.room.entity.Entity;
import com.nhg.game.domain.room.entity.component.ComponentType;
import com.nhg.game.domain.room.entity.component.ItemComponent;
import com.nhg.game.domain.room.entity.component.UserComponent;
import com.nhg.game.domain.shared.position.Position2;
import com.nhg.game.domain.user.User;

@UseCase
public class RoomSharedUseCase {

    public User getUserFromEntity(Entity userEntity) {
        UserComponent userComponent = (UserComponent) userEntity.getComponent(ComponentType.User);
        return userComponent.getUser();
    }

    public RoomItem getItemFromEntity(Entity itemEntity) {
        ItemComponent itemComponent = (ItemComponent) itemEntity.getComponent(ComponentType.Item);
        return itemComponent.getItem();
    }

    public void updateRoomTile(Room room, RoomItem item) {
        Position2 pos = item.getPosition().toPosition2();

        //TODO: update tile at position
    }
}
