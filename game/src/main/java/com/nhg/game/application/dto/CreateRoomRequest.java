package com.nhg.game.application.dto;

import com.nhg.game.domain.user.User;

public record CreateRoomRequest(
        String name,
        String description,
        String layout,
        User owner,
        int doorX,
        int doorY,
        int doorDirection,
        int maxUsers
) {
}
