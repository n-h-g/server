package com.cubs3d.messenger.dto;

public record ChatMessageDto(Integer senderId, Integer destinationId, String text, boolean isRoomMessage) {
}
