package com.nhg.game.domain.item;

import lombok.Getter;

@Getter
public enum ItemType {
    FLOOR_ITEM("FLOOR_ITEM"),
    WALL_ITEM("WALL_ITEM");

    private final String value;

    ItemType(String value) {
        this.value = value;
    }
}