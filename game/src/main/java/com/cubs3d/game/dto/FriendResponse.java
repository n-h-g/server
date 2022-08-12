package com.cubs3d.game.dto;

public record FriendResponse(
        Integer friendshipId,
        Integer senderId,
        Integer destinationId,
        boolean pending
) {

}
