package com.nhg.game.command.impl;

import com.nhg.game.command.Command;

public class TestCommand implements Command {
    @Override
    public String getName() {
        return "test";
    }

    @Override
    public void execute(String... params) {

    }
}
