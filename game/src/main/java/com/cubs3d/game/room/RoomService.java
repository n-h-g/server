package com.cubs3d.game.room;

import com.cubs3d.game.GameConfig;
import com.cubs3d.game.user.User;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

@Slf4j
@Service
public class RoomService {

    /**
     * The period between successive executions of the room task.
     *
     * @see #startRoomTask
     */
    private static final int RoomFixedRateScheduleMS = 480;

    /**
     * Seconds before an empty room (0 users inside) gets unloaded.
     *
     * @see #checkEmptyRoomAndScheduleUnload 
     */
    private static final int SecondsBeforeEmptyRoomGetsUnloaded = 10;

    /**
     * Map of active rooms' tasks.
     * Used to retrieve the task associated with a room to stop it.
     *
     * @see #startRoomTask
     * @see #checkEmptyRoomAndScheduleUnload
     */
    private final Map<Integer, ScheduledFuture<Room>> activeRoomsTasks = new ConcurrentHashMap<>();

    /**
     * @see GameConfig#getTaskScheduler
     */
    @Autowired
    @Qualifier("getTaskScheduler")
    private TaskScheduler scheduler;

    private RoomRepository roomRepository;


    /**
     * The specified user enters the room with the specified id then start the room's task.
     *
     * @param user user that is entering the room.
     * @param roomId target room's id.
     * @see Room#userEnter
     * @see #startRoomTask
     */
    public void userEnterRoom(@NonNull User user, @NonNull Integer roomId) {
        Room room = this.getRoomById(roomId);

        if (room == null) return;

        room.userEnter(user);
        this.startRoomTask(room);

        log.debug("User "+ user.getId() +" entered room "+ roomId);
    }

    /**
     * If the specified room is not contained inside the '<code>activeRoomsTasks</code>', it's added and the room's
     * task is started and executed every '<code>RoomFixedRateScheduleMS</code>' milliseconds.
     *
     * @param room room whose task needs to be started.
     * @see #activeRoomsTasks
     * @see #RoomFixedRateScheduleMS
     */
    private void startRoomTask(@NonNull Room room) {
        if (activeRoomsTasks.containsKey(room.getId())) return;

        @SuppressWarnings("unchecked")
        ScheduledFuture<Room> task = (ScheduledFuture<Room>) scheduler
                .scheduleAtFixedRate(room, RoomFixedRateScheduleMS);

        activeRoomsTasks.put(room.getId(), task);

        log.debug("Task started for room "+ room.getId());
    }

    /**
     * The specified user exits the room with the specified id.
     *
     * @param user user that is exiting the room.
     * @param roomId target room's id.
     * @see Room#userExit
     * @see #checkEmptyRoomAndScheduleUnload
     */
    public void userExitRoom(@NonNull User user, @NonNull Integer roomId) {
        Room room = this.getRoomById(roomId);

        if (room == null) return;

        room.userExit(user);
        this.checkEmptyRoomAndScheduleUnload(room);

        log.debug("User "+ user.getId() +" exited room "+ roomId);
    }

    /**
     * Check the room: if it doesn't contain users after '<code>SecondsBeforeEmptyRoomGetsUnloaded</code>' seconds
     * it will check again that it's not empty then stop the room task.
     *
     * @param room room that needs to be checked.
     * @see Room#usersCount
     * @see #SecondsBeforeEmptyRoomGetsUnloaded
     */
    private void checkEmptyRoomAndScheduleUnload(@NonNull Room room) {
        if (room.usersCount() > 0) return;

        scheduler.schedule(() -> {
            // Check again if the room is empty
            if (room.usersCount() > 0) return;

            // Remove the room from activeRoomsTasks
            ScheduledFuture<Room> task = activeRoomsTasks.remove(room.getId());

            // Stop the room's task
            if (task.cancel(true)) {
                log.debug("Task stopped for room "+ room.getId());
            }

        }, new Date(System.currentTimeMillis() + SecondsBeforeEmptyRoomGetsUnloaded * 1000));
    }

    /**
     * Returns the room the specified id, or null if it doesn't exist.
     *
     * @param id room id
     * @return the room the specified id, or null if it doesn't exist.
     */
    public Room getRoomById(Integer id) {
        return roomRepository
                .findById(id)
                .orElse(null);
    }
}
