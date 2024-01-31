package com.nhg.game.domain.room.entity;

import lombok.Getter;
import lombok.experimental.Accessors;

public enum Action {
    MOVE("Move"),
    LAY("Lay", true),
    SIT("Sit", true),
    SLEEP("Sleep", true),
    HAND_ITEM("CarryItem"),
    SIGN("Sign");

    @Getter
    private final String value;

    @Getter
    @Accessors(fluent = true)
    private final boolean shouldBeRemovedOnMove;

    Action(String value) {
        this.value = value;
        this.shouldBeRemovedOnMove = false;
    }

    Action(String value, boolean removeActionOnMove) {
        this.value = value;
        this.shouldBeRemovedOnMove = removeActionOnMove;
    }
}
