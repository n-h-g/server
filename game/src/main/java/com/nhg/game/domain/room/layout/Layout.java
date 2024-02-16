package com.nhg.game.domain.room.layout;

import com.nhg.game.domain.shared.position.Position2;

public interface Layout {
    Tile getTile(Position2 position) throws IndexOutOfBoundsException;
}
