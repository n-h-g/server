package com.nhg.game.utils;

public enum Gender {
    MALE('m'),
    FEMALE('f');

    private final char symbol;

    Gender(char symbol) {
        this.symbol = symbol;
    }

    public char toChar() {
        return symbol;
    }
}
