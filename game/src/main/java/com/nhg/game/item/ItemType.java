package com.nhg.game.item;

import lombok.Getter;

public enum ItemType {
    FLOOR_ITEM("FLOOR_ITEM"),
    WALL_ITEM("WALL_ITEM");

    @Getter
    private final String value;

    ItemType(String value) {
        this.value = value;
    }
}
