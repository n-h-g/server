package com.cubs3d.game.room.entity;

import com.cubs3d.game.room.Room;
import com.cubs3d.game.utils.Int3;
import lombok.Getter;
import lombok.Setter;


public abstract class RoomEntity {

    @Getter
    private final Integer id;

    @Getter
    @Setter
    private Int3 position;

    @Getter
    private final Room room;

    protected RoomEntity(Integer id, Room room) {
        this.id = id;
        this.room = room;
    }

    public void cycle() {}

}
