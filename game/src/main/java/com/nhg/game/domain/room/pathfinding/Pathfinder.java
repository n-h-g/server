package com.nhg.game.domain.room.pathfinding;

import com.nhg.game.domain.room.layout.Layout;
import com.nhg.game.domain.room.layout.Tile;

import java.util.Queue;

public interface Pathfinder {

    Queue<Tile> findPath(Tile start, Tile target, Layout layout);

}
