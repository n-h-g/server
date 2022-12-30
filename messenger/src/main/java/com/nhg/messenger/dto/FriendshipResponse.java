package com.nhg.messenger.dto;

import com.nhg.messenger.model.Friendship;

import java.util.List;

public record FriendshipResponse(
        List<Friendship> friendships
) {
}
