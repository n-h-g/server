package com.nhg.game.application.dto;

import com.nhg.game.domain.shared.human.Gender;
import com.nhg.game.domain.user.User;

public record UserResponse(
    int id,
    String username,
    String motto,
    Gender gender,
    String look,
    int credits
) {

    public static UserResponse fromDomain(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getMotto(),
                user.getGender(),
                user.getLook(),
                user.getCredits()
        );
    }
}