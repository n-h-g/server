package com.nhg.game.item;

import lombok.Getter;

public enum ItemType {
    FLOOR_ITEM("floor_item"),
    WALL_ITEM("wall_item");

    @Getter
    private final String value;

    ItemType(String value) {
        this.value = value;
    }
}
