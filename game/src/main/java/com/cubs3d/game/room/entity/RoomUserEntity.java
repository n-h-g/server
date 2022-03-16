package com.cubs3d.game.room.entity;

import com.cubs3d.game.room.Room;
import com.cubs3d.game.user.User;
import lombok.Getter;

public class RoomUserEntity extends RoomEntity {

    @Getter
    private final User user;

    public RoomUserEntity(Integer id, Room room, User user) {
        super(id, room);

        this.user = user;
    }
}
