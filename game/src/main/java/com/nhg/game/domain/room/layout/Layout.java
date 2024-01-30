package com.nhg.game.domain.room.layout;

public interface Layout {
    Tile getTile(int x, int y) throws IndexOutOfBoundsException;
}
