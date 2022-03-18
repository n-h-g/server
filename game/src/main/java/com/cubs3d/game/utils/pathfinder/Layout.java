package com.cubs3d.game.utils.pathfinder;

public interface Layout {
    Tile getTile(int x, int y) throws IndexOutOfBoundsException;
}
