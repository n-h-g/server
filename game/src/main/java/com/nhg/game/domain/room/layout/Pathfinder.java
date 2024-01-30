package com.nhg.game.domain.room.layout;

import java.util.Queue;

public interface Pathfinder {

    Queue<Tile> findPath(Tile start, Tile target, Layout layout);

}
