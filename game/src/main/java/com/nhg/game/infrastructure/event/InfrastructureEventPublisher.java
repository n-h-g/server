package com.nhg.game.infrastructure.event;

public interface InfrastructureEventPublisher {

    void publish(InfrastructureEvent event);
}
