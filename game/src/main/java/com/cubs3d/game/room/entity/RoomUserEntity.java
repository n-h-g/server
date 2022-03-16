package com.cubs3d.game.room.entity;

import com.cubs3d.game.room.Room;
import com.cubs3d.game.room.entity.component.ComponentType;
import com.cubs3d.game.room.entity.component.PlayerInputComponent;
import com.cubs3d.game.user.User;

public class RoomUserEntity extends RoomEntity {

    public RoomUserEntity(Room room, User user) {
        super(room);

        this.addComponent(ComponentType.PLAYER_INPUT);
        this.addComponent(ComponentType.MOVEMENT);

        PlayerInputComponent pi = this.getComponent(ComponentType.PLAYER_INPUT, PlayerInputComponent.class);
        pi.setUser(user);
    }
}
