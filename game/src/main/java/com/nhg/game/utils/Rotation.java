package com.nhg.game.utils;

import lombok.Getter;

public enum Rotation {
    NORTH(6),
    NORTH_EAST(7),
    EAST(0),
    SOUTH_EAST(1),
    SOUTH(2),
    SOUTH_WEST(3),
    WEST(4),
    NORTH_WEST(5);

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
        if (point1.getX() > point2.getX()) {
            if (point1.getY() > point2.getY()) {
                return Rotation.SOUTH_WEST;
            }
            if (point1.getY() < point2.getY()) {
                return Rotation.NORTH_WEST;
            }
            return Rotation.WEST;
        }

        if (point1.getX() < point2.getX()) {
            if (point1.getY() > point2.getY()) {
                return Rotation.SOUTH_EAST;
            }
            if (point1.getY() < point2.getY()) {
                return Rotation.NORTH_EAST;
            }
            return Rotation.EAST;
        }

        if (point1.getY() > point2.getY()) {
            return Rotation.NORTH;
        }

        return SOUTH;
    }
}
