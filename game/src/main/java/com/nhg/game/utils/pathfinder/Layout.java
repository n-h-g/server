package com.nhg.game.utils.pathfinder;

public interface Layout {
    int getMapSizeY();
    int getMapSizeX();
    Tile getTile(int x, int y) throws IndexOutOfBoundsException;
}
