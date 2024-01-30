package com.nhg.game.application.dto;

import com.nhg.game.domain.shared.human.Gender;
import com.nhg.game.domain.user.User;

public record UserDto(
    int id,
    String username,
    String motto,
    Gender gender,
    String look,
    int credits
) {

    public static UserDto fromDomain(User user) {
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getMotto(),
                user.getGender(),
                user.getLook(),
                user.getCredits()
        );
    }
}