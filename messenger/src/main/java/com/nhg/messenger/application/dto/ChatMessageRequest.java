package com.nhg.messenger.application.dto;

public record ChatMessageRequest(
        Integer senderId,
        Integer recipientId,
        String text,
        boolean isRoomMessage) {
}
