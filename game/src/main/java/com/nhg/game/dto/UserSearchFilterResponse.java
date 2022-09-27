package com.nhg.game.dto;

import com.nhg.game.user.User;

import java.util.List;

public record UserSearchFilterResponse(List<User> users) {
}
