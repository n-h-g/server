package com.cubs3d.game.utils;

import lombok.Getter;

public enum Rotation {
    NORTH(6),
    NORTH_EAST(5),
    EAST(4),
    SOUTH_EAST(3),
    SOUTH(2),
    SOUTH_WEST(1),
    WEST(0),
    NORTH_WEST(7);

    @Getter
    private final int value;

    Rotation(int value) {
        this.value = value;
    }

    public static Rotation intToRotation(int value) {
        value = Math.abs(value % 8);

        for (Rotation rot : Rotation.values()) {
            if (rot.getValue() == value) return rot;
        }

        return NORTH;
    }

    public static Rotation CalculateRotation(Int2 point1, Int2 point2) {
        Rotation rotation;

        if (point1.getX() > point2.getX() && point1.getY() > point2.getY()) {
            rotation = Rotation.NORTH_WEST;
        } else if (point1.getX() > point2.getX() && point1.getY() < point2.getY()) {
            rotation = Rotation.SOUTH_WEST;
        } else if (point1.getX() < point2.getX() && point1.getY() > point2.getY()) {
            rotation = Rotation.NORTH_EAST;
        } else if (point1.getX() < point2.getX() && point1.getY() < point2.getY()) {
            rotation = Rotation.SOUTH_EAST;
        } else if (point1.getX() > point2.getX()) {
            rotation = Rotation.WEST;
        } else if (point1.getX() < point2.getX()) {
            rotation = Rotation.EAST;
        } else if (point1.getY() > point2.getY()) {
            rotation = Rotation.NORTH;
        } else {
            rotation = Rotation.SOUTH;
        }

        return rotation;
    }



}
