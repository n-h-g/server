package com.nhg.game.application.event;

import com.nhg.common.domain.event.DomainEvent;

public interface GameMessagePublisher {

    void publish(DomainEvent event);
}
