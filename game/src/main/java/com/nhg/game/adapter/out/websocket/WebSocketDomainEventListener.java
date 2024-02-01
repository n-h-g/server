package com.nhg.game.adapter.out.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhg.common.domain.event.DomainEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketDomainEventListener {

    private final ObjectMapper objectMapper;

    @EventListener
    public void handleEvent(DomainEvent domainEvent) {
        try {
            switch (domainEvent) {
                default -> { }
            }
        } catch (Exception e) {
            log.error("WebsocketDomainEventListener: Error while handling event");
        }
    }
}
