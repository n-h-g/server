package com.nhg.game.application.usecase.room;

import com.nhg.game.domain.item.RoomItem;
import com.nhg.game.domain.room.Room;
import com.nhg.game.domain.shared.position.Position2;

public final class RoomUtils {

    public static void updateRoomTile(Room room, RoomItem item) {
        Position2 pos = item.getPosition().toPosition2();

        //TODO: update tile at position
    }
}
