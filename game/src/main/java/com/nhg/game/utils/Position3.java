package com.nhg.game.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Structure to represent positions in a 2D space tiled-based using the z as height.
 */
@Data
@AllArgsConstructor
public class Position3 {

    public static final Position3 Zero = new Position3(0,0,0);

    private int x;
    private int y;
    private float z;

    public Position2 toPosition2() {
        return new Position2(x,y);
    }
}
