package com.cubs3d.game.dto;

public record ChatMessageRequest(
        Integer senderId,
        Integer destinationId,
        String text,
        boolean isRoomMessage
) { }
