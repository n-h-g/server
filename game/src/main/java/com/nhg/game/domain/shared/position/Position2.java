package com.nhg.game.domain.shared.position;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Structure to represent positions in a 2D space tiled-based.
 */
@Data
@EqualsAndHashCode
@AllArgsConstructor
public class Position2 {

    private int x;
    private int y;

    @Override
    public String toString() {
        return "("+ x +";"+ y +")";
    }
}
