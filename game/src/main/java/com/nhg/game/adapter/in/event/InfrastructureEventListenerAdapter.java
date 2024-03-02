package com.nhg.game.adapter.in.event;

import com.nhg.game.application.usecase.room.entity.LoadRoomEntitiesUseCase;
import com.nhg.game.infrastructure.event.InfrastructureEvent;
import com.nhg.game.infrastructure.event.room.RoomTaskStartedEvent;
import com.nhg.game.infrastructure.event.room.RoomTaskStoppedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class InfrastructureEventListenerAdapter {

    private final LoadRoomEntitiesUseCase loadRoomEntitiesUseCase;
    private final TaskExecutor taskExecutor;

    @EventListener
    public void handleEvent(InfrastructureEvent infrastructureEvent) {
        try {
            switch (infrastructureEvent) {
                case RoomTaskStartedEvent event -> {
                    taskExecutor.execute(() -> loadRoomEntitiesUseCase.loadRoomEntities(event.getRoomId()));
                }


                case RoomTaskStoppedEvent event -> {

                }

                default -> { }
            }
        } catch (Exception e) {
            log.error("InfrastructureEventListenerAdapter: Error while handling event");
        }
    }
}
