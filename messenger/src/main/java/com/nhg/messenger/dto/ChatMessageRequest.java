package com.nhg.messenger.dto;

public record ChatMessageRequest(Integer senderId, Integer destinationId, String text, boolean isRoomMessage) {
}
