package com.nhg.game.user;

import com.nhg.game.utils.BeanRetriever;
import lombok.NonNull;

import jakarta.persistence.PostLoad;

public class UserListener {

    @PostLoad
    private void postLoad(@NonNull User user) {
        UserService userService = BeanRetriever.get(UserService.class);

        User activeUser = userService.getActiveUserById(user.getId());

        if (activeUser == null) return;

        user.setOnline(true);
    }
}
