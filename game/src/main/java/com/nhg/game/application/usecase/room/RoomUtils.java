package com.nhg.game.application.usecase.room;

import com.nhg.game.domain.room.layout.RoomLayout;
import com.nhg.game.domain.room.layout.Tile;
import com.nhg.game.domain.shared.position.Position2;

public final class RoomUtils {

    public static void updateRoomHeightmapAt(RoomLayout layout, Position2 position, float height) {
        layout.setHeightAtPosition(position, height);
    }

    public static void updateRoomTileStateAt(RoomLayout layout, Position2 position, Tile.State state) {
        layout.getTile(position).setState(state);
    }

    public static void updateRoomLayoutAt(RoomLayout layout, Position2 position, Tile.State state, float height) {
        updateRoomTileStateAt(layout, position, state);
        updateRoomHeightmapAt(layout, position, height);
    }
}
