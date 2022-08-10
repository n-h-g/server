package com.cubs3d.messenger.dto;

import com.cubs3d.messenger.friendship.Friendship;

import java.util.List;

public record FriendshipResponse(
        List<Friendship> friendships
) {
}
