package com.cubs3d.messenger.dto;

public record FriendResponse(
        Integer friendshipId,
        Integer senderId,
        Integer destinationId,
        boolean pending
) {
}
