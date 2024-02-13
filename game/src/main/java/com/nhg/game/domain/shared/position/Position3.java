package com.nhg.game.domain.shared.position;

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

    public Position3(Position2 position2, float z) {
        this.x = position2.getX();
        this.y = position2.getY();
        this.z = z;
    }

    public Position2 toPosition2() {
        return new Position2(x,y);
    }

    @Override
    public String toString() {
        return "("+ x +";"+ y +";"+ z + ")";
    }
}
