package com.nhg.game.item;

import lombok.Getter;

public enum ItemType {
    FLOOR_ITEM("flooritem"),
    WALL_ITEM("wallitem");

    @Getter
    private final String value;

    ItemType(String value) {
        this.value = value;
    }
}
