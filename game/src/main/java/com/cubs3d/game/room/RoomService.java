package com.cubs3d.game.room;

import com.cubs3d.game.GameConfig;
import com.cubs3d.game.user.User;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledFuture;

@Slf4j
@Service
@AllArgsConstructor
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
    private final Map<Integer, ScheduledFuture<?>> activeRoomsTasks = new ConcurrentHashMap<>();

    /**
     * Map of active rooms.
     */
    private final Map<Integer, Room> activeRooms = new ConcurrentHashMap<>();

    /**
     * @see GameConfig#taskScheduler
     */
    @Qualifier("taskScheduler")
    private final TaskScheduler taskScheduler;

    private final RoomRepository roomRepository;


    /**
     * The specified user enters the room with the specified id then start the room's task.
     *
     * @param user user that is entering the room.
     * @param roomId target room's id.
     * @return false on failure.
     * @see Room#userEnter
     * @see #startRoomTask
     */
    public boolean userEnterRoom(@NonNull User user, @NonNull Integer roomId) {
        Room room = this.getRoomById(roomId);

        if (room == null) return false;

        room.userEnter(user);
        this.startRoomTask(room);

        log.debug("User "+ user.getId() +" entered room "+ roomId);
        return true;
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
        ScheduledFuture<?> task = taskScheduler.scheduleAtFixedRate(room, RoomFixedRateScheduleMS);

        activeRoomsTasks.put(room.getId(), task);
        activeRooms.putIfAbsent(room.getId(), room);

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

        taskScheduler.schedule(() -> {
            // Check again if the room is empty
            if (room.usersCount() > 0) return;

            // Remove the room from activeRoomsTasks
            ScheduledFuture<?> task = activeRoomsTasks.remove(room.getId());

            activeRooms.remove(room.getId());

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
        return activeRooms.containsKey(id)
            ? activeRooms.get(id)
            : roomRepository.findById(id).orElse(null);
    }

    /**
     * Get all active rooms. A room is <i>active</i> when it's contained in <code>activeRoomsTasks</code>.
     *
     * @return list of active rooms.
     * @see #activeRoomsTasks
     */
    public List<Room> getActiveRooms() {
        return activeRooms.values().stream().toList();
    }

    /**
     * Get the rooms owned by the given user.
     *
     * @param owner rooms' owner
     * @return rooms owned by the given user.
     */
    public List<Room> getRoomsByOwner(User owner) {
        return roomRepository.findByOwner(owner);
    }
}
