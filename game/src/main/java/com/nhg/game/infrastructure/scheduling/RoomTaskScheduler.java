package com.nhg.game.infrastructure.scheduling;

import com.nhg.common.domain.event.DomainEvent;
import com.nhg.game.application.event.room.RoomActivatedEvent;
import com.nhg.game.application.event.room.RoomDeletedEvent;
import com.nhg.game.application.event.room.UserExitRoomEvent;
import com.nhg.game.application.repository.ActiveRoomRepository;
import com.nhg.game.domain.room.Room;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

@Slf4j
@Component
@RequiredArgsConstructor
public class RoomTaskScheduler {

    /**
     * The period between successive executions of the room task.
     *
     * @see #startRoomTask
     */
    private static final int MillisBetweenRoomUpdates = 480;

    /**
     * Seconds before an empty room gets unloaded.
     *
     * @see #checkEmptyRoomAndScheduleStop
     */
    private static final int SecondsBeforeEmptyRoomGetsUnloaded = 10;

    @Qualifier("taskScheduler")
    private final TaskScheduler taskScheduler;

    private final ActiveRoomRepository activeRoomRepository;

    private final Map<Integer, ScheduledFuture<?>> activeRoomsTasks = new ConcurrentHashMap<>();


    @EventListener
    public void handleEvent(DomainEvent domainEvent) {

        switch (domainEvent) {
            case RoomActivatedEvent event ->
                    activeRoomRepository.findById(event.getRoomId()).ifPresent(this::startRoomTask);

            case RoomDeletedEvent event ->
                    activeRoomRepository.findById(event.getRoomId()).ifPresent(this::stopRoomTask);

            case UserExitRoomEvent event ->
                    activeRoomRepository.findById(event.getRoomId()).ifPresent(this::checkEmptyRoomAndScheduleStop);

            default -> { }
        }
    }


    /**
     * If the specified room is not contained inside the '<code>activeRoomsTasks</code>' then it's added and the room's
     * task is started and executed every '<code>MillisBetweenRoomUpdates</code>' milliseconds.
     *
     * @param room room whose task needs to be started.
     * @see #activeRoomsTasks
     * @see #MillisBetweenRoomUpdates
     */
    private void startRoomTask(@NonNull Room room) {
        if (activeRoomsTasks.containsKey(room.getId())) return;

        ScheduledFuture<?> task = taskScheduler.scheduleAtFixedRate(room, Duration.ofMillis(MillisBetweenRoomUpdates));

        activeRoomsTasks.put(room.getId(), task);

        log.debug("Task started for room "+ room.getId());
    }

    /**
     * Check if the room is empty, then if it still doesn't contain users after
     * '<code>SecondsBeforeEmptyRoomGetsUnloaded</code>' seconds stop the room task.
     *
     * @param room room that needs to be checked.
     */
    private void checkEmptyRoomAndScheduleStop(@NonNull Room room) {
        if (!room.isEmpty()) return;

        taskScheduler.schedule(() -> {
            // Check again if the room is empty
            if (!room.isEmpty()) return;

            stopRoomTask(room);

        }, Instant.now().plusSeconds(SecondsBeforeEmptyRoomGetsUnloaded));
    }

    /**
     * Stop the room's task.
     *
     * @param room room to stop.
     */
    private void stopRoomTask(@NonNull Room room) {
        // Remove the room from activeRoomsTasks
        ScheduledFuture<?> task = activeRoomsTasks.remove(room.getId());

        activeRoomRepository.remove(room.getId());

        // Stop the room's task
        if (task != null && task.cancel(true)) {
            log.debug("Task stopped for room "+ room.getId());
        }
    }
}
