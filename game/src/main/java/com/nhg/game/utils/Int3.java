package com.nhg.game.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 3 integer element structure to represent positions in a 2D space tiled-based using the z as height.
 */
@Data
@AllArgsConstructor
public class Int3 {

    private int x;
    private int y;
    private int z;

    public Int2 ToInt2XY() {
        return new Int2(x,y);
    }
}
