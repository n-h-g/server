package com.nhg.game.room.entity;

public enum Action {
    MOVE("Move"),
    LAY("Lay", true),
    SIT("Sit", true),
    SLEEP("Sleep", true),
    HAND_ITEM("CarryItem"),
    SIGN("Sign");

    private final String value;
    private final boolean removeActionOnMove;

    Action(String value) {
        this.value = value;
        this.removeActionOnMove = false;
    }

    Action(String value, boolean removeActionOnMove) {
        this.value = value;
        this.removeActionOnMove = removeActionOnMove;
    }

    public String getValue() {
        return value;
    }

    public boolean shouldBeRemovedOnMove() {
        return removeActionOnMove;
    }
}
