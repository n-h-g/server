package com.nhg.game.utils.events;

public interface EventListener {
    void notify(Event event, Object... params);
}
