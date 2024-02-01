package com.nhg.common.domain.event;

public interface DomainEventPublisher {

    void publish(DomainEvent event);

}
