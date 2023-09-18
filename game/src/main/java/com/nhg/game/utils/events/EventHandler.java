package com.nhg.game.utils.events;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class EventHandler {

    private final Map<Event, List<EventListener>> eventPool;

    public EventHandler(Event... events) {
        eventPool = new EnumMap<>(Event.class);

        for (Event event : events) {
            eventPool.putIfAbsent(event, new ArrayList<>());
        }
    }

    /**
     * Subscribe the given EventListener to the given Event.
     * It will be notified when the event is emitted.
     * 
     * @param event the event to subscribe to.
     * @param listener the event's listener.
     * 
     * @see #emit
     */
    public void subscribe(Event event, EventListener listener) {
        List<EventListener> listeners = eventPool.get(event);
        listeners.add(listener);
    }

    /**
     * Unsubscribe the given EventListener to the given Event.
     * It will no longer be notified from the event emission.
     *
     * @param event  the event to unsubscribe to.
     * @param listener the event's listener.
     */
    public void unsubscribe(Event event, EventListener listener) {
        List<EventListener> listeners = eventPool.get(event);
        listeners.remove(listener);
    }

    /**
     * Emit the given event. All subscribers (EventListener) will be notified.
     * 
     * @param event the event to emit.
     * @param params additional parameters that must be passed to the listener.
     * 
     * @see EventListener#notify
     */
    public void emit(Event event, Object... params) {
        List<EventListener> listeners = eventPool.get(event);
        for (EventListener listener : listeners) {
            listener.notify(event, params);
        }
    }
}
