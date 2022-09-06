package com.nhg.messenger.dto;

public record FriendResponse(
        Integer friendshipId,
        Integer senderId,
        Integer destinationId,
        boolean pending
) {
}
