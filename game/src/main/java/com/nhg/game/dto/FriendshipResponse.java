package com.nhg.game.dto;
import java.util.List;


public record FriendshipResponse(
        List<FriendResponse> friendships
) {
}
