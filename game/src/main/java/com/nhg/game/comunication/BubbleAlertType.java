package com.nhg.game.comunication;

import lombok.Getter;

public enum BubbleAlertType {
    Default("default_bubble_action"),
    Friend("friend_bubble_action"),
    Purchase("purchase_bubble_action");

    @Getter
    private final String value;

    BubbleAlertType(String value) {
        this.value = value;
    }
}
