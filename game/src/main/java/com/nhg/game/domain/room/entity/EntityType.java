package com.nhg.game.domain.room.entity;

import lombok.Getter;

public enum EntityType {
    HUMAN("h"),
    ITEM("i");

    @Getter
    private final String code;

    EntityType(String code) {
        this.code = code;
    }
}
