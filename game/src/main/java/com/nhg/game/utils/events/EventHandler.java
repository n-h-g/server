package com.nhg.game.utils.events;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class EventHandler {

    private final Map<Event, List<EventListener>> eventPool;

    public EventHandler(Event... events) {
        eventPool = new EnumMap<>(Event.class);

        for (Event event : events) {
            eventPool.putIfAbsent(event, new ArrayList<>());
        }
    }

    public void subscribe(Event event, EventListener listener) {
        List<EventListener> listeners = eventPool.get(event);
        listeners.add(listener);
    }

    public void unsubscribe(Event event, EventListener listener) {
        List<EventListener> listeners = eventPool.get(event);
        listeners.remove(listener);
    }

    public void emit(Event event, Object... obj) {
        log.warn("emit");
        List<EventListener> listeners = eventPool.get(event);
        for (EventListener listener : listeners) {
            listener.notify(event, obj);
        }
    }
}
