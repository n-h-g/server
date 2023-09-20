package com.nhg.game.command;

public interface Command {

    String getName();
    void execute(String... params) throws Exception;
}
