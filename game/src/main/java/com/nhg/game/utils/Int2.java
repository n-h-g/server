package com.nhg.game.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 2 integer element structure to represent positions in a 2D space tiled-based.
 */
@Data
@EqualsAndHashCode
@AllArgsConstructor
public class Int2 {

    private int x;
    private int y;

    @Override
    public String toString() {
        return "("+ x +";"+ y +")";
    }
}
