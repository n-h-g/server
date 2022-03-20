package com.cubs3d.messenger.dto;

public record ChatMessageRequest(Integer senderId, Integer destinationId, String text, boolean isRoomMessage) {
}
