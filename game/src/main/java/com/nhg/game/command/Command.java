package com.nhg.game.command;

import com.nhg.game.user.User;
import lombok.NonNull;

public interface Command {

    String getName();
    void execute(@NonNull User user, String... params) throws Exception;
}
