package com.nhg.game.application.exception;

import lombok.Getter;

@Getter
public enum ProblemCode {
    ROOM_DOES_NOT_EXIST("room_does_not_exist");


    private final String code;

    ProblemCode(String code) {
        this.code = code;
    }
}
