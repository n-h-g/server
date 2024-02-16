package com.nhg.game.application.exception;

import lombok.Getter;

@Getter
public enum ProblemCode {
    ROOM_DOES_NOT_EXIST("room_does_not_exist"),
    ENTITY_DOES_NOT_EXIST("entity_does_not_exist"),

    MAX_ITEMS("max_items"),
    MAX_HEIGHT("max_height");


    private final String code;

    ProblemCode(String code) {
        this.code = code;
    }
}
