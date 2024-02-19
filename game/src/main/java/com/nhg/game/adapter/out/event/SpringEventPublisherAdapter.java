package com.nhg.game.adapter.out.event;

import com.nhg.common.domain.event.DomainEvent;
import com.nhg.common.domain.event.DomainEventPublisher;
import com.nhg.game.infrastructure.event.InfrastructureEvent;
import com.nhg.game.infrastructure.event.InfrastructureEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SpringEventPublisherAdapter implements DomainEventPublisher, InfrastructureEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void publish(DomainEvent event) {
        applicationEventPublisher.publishEvent(event);
    }

    @Override
    public void publish(InfrastructureEvent event) {
        applicationEventPublisher.publishEvent(event);
    }
}
