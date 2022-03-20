package com.cubs3d.game.room.entity;

import lombok.Getter;

public enum EntityType {
    HUMAN("h");

    @Getter
    private final String code;

    EntityType(String code) {
        this.code = code;
    }
}
