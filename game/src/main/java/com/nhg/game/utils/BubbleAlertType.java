package com.nhg.game.utils;

import lombok.Getter;

public enum BubbleAlertType {
    Default("DefaultBubbleAction"),
    Friend("FriendBubbleAction"),
    Purchase("PurchaseInfoAction");

    @Getter
    private final String value;

    BubbleAlertType(String value) {
        this.value = value;
    }
}
