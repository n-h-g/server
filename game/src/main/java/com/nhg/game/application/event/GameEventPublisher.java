package com.nhg.game.application.event;

import com.nhg.common.domain.event.DomainEvent;

public interface GameEventPublisher {

    void publish(DomainEvent event);
}
