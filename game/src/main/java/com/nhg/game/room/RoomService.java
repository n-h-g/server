package com.nhg.game.room;

import com.nhg.game.GameConfig;
import com.nhg.game.item.Item;
import com.nhg.game.item.ItemService;
import com.nhg.game.npc.bot.Bot;
import com.nhg.game.npc.bot.BotService;
import com.nhg.game.room.entity.Entity;
import com.nhg.game.user.User;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
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
     * Seconds before an empty room gets unloaded.
     *
     * @see #checkEmptyRoomAndScheduleUnload
     * @see Room#isEmpty
     */
    private static final int SecondsBeforeEmptyRoomGetsUnloaded = 10;

    /**
     * Map of active rooms' tasks.
     * Used to retrieve the task associated with a room to stop it.
     *
     * @see #startRoomTask
     * @see #checkEmptyRoomAndScheduleUnload
     * @see #activeRooms
     */
    private final Map<Integer, ScheduledFuture<?>> activeRoomsTasks = new ConcurrentHashMap<>();

    /**
     * Map of active rooms.
     *
     * @see #activeRoomsTasks
     */
    private final Map<Integer, Room> activeRooms = new ConcurrentHashMap<>();

    /**
     * @see GameConfig#taskScheduler
     */
    @Qualifier("taskScheduler")
    private final TaskScheduler taskScheduler;

    private final RoomRepository roomRepository;

    private final ItemService itemService;
    private final BotService botService;


    /**
     * (Create) If a room with a null id is passed then it will create a new room and generate an id for it.
     * (Update) If the room already have an id it will update the existing room with the params of the given room.
     *
     * @param room room to save
     * @return the saved room
     */
    public Room save(Room room) {
        return roomRepository.save(room);
    }

    /**
     * Unload the room then delete it from database.
     * 
     * @param room room to delete
     * @see ItemService#retrieveRoomItemsToRoomOwner 
     * @see #unloadRoom
     */
    public void delete(Room room) {
        unloadRoom(room);
        itemService.retrieveRoomItemsToRoomOwner(room);
        roomRepository.delete(room);
    }

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

        if(user.getEntity() != null) return false;

        this.prepareRoom(room);

        room.userEnter(user);

        log.debug("User "+ user.getId() +" entered room "+ roomId);
        return true;
    }

    /**
     * Prepare the room to activate it
     *
     * @param room the room to be activated.
     * @see #startRoomTask
     * @see #loadRoomEntities 
     */
    private void prepareRoom(@NonNull Room room) {
        if (activeRoomsTasks.containsKey(room.getId())) return;

        this.startRoomTask(room);
        this.loadRoomEntities(room);
    }

    /**
     * Load room entities for the given room.
     *
     * @param room the room that loads the entities.
     * @see ItemService#getItemsByRoom
     * @see BotService#getBotsByRoom
     */
    private void loadRoomEntities(@NonNull Room room) {
        List<Item> items = itemService.getItemsByRoom(room);
        List<Bot> bots = botService.getBotsByRoom(room);

        for (Item item : items) {
            placeItem(item, room);
        }

        for (Bot bot : bots) {
            room.addEntity(Entity.fromBot(bot, room));
        }
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

        ScheduledFuture<?> task = taskScheduler.scheduleAtFixedRate(room, RoomFixedRateScheduleMS);

        activeRoomsTasks.put(room.getId(), task);
        activeRooms.putIfAbsent(room.getId(), room);

        log.debug("Task started for room "+ room.getId());
    }

    /**
     * The specified user exits the room with the specified id.
     *
     * @param user the user that is exiting the room.
     * @param roomId target room's id.
     * @see Room#userExit
     * @see #checkEmptyRoomAndScheduleUnload
     */
    public boolean userExitRoom(@NonNull User user, int roomId) {
        Room room = this.getRoomById(roomId);

        if (room == null) return false;

        room.userExit(user);
        this.checkEmptyRoomAndScheduleUnload(room);

        log.debug("User "+ user.getId() +" exited room "+ roomId);
        return true;
    }

    /**
     * The specified user exits the room he's in. If the user is not in any room it returns.
     * The room is taken from the user's entity.
     *
     * @param user the user that is exiting the room
     * @see #userExitRoom
     */
    public void userExitRoom(@NonNull User user) {
        if (user.getEntity() == null) return;

        userExitRoom(user, user.getEntity().getRoom().getId());
    }

    /**
     * Creates an entity for the given item, then adds it to the room.
     *
     * @param item the item placed.
     * @param room the room where the item is placed.
     */
    @Transactional
    public Entity placeItem(@NonNull Item item, @NonNull Room room) {
        if (!item.getItemSpecification().isAllowWalk()) {
            getActiveRoomById(room.getId()).blockTile(item.getPosition().getX(), item.getPosition().getY());
        }

        Entity entity = Entity.fromItem(item, room);
        room.addEntity(entity);

        return entity;
    }

    /**
     * Updates the given item state.
     * @param item the item to be updated.
     * @param room the room where the item is placed.
     */
    @Transactional
    public void updateItem(@NonNull Item item, @NonNull Room room) {
        if (!item.getItemSpecification().isAllowWalk()) {
            getActiveRoomById(room.getId()).blockTile(item.getPosition().getX(), item.getPosition().getY());
        }
    }

    /**
     * Removes the picked up item from the room
     *
     * @param user the user that is picking up the item.
     * @param item the item picked up.
     * @param room the room where the item is picked up.
     * @see ItemService#userPickUpItem 
     */
    @Transactional
    public void pickUpItem(@NonNull User user, @NonNull Item item, @NonNull Room room) {
        if (!item.getItemSpecification().isAllowWalk()) {
            getActiveRoomById(room.getId()).openTile(item.getPosition().getX(), item.getPosition().getY());
        }

        room.removeEntity(item.getEntity());

        itemService.userPickUpItem(user, item);
    }

    /**
     * Check the room: if it doesn't contain users after '<code>SecondsBeforeEmptyRoomGetsUnloaded</code>' seconds
     * it will check again that it's not empty then stop the room task.
     *
     * @param room room that needs to be checked.
     * @see Room#isEmpty
     * @see #unloadRoom
     * @see #SecondsBeforeEmptyRoomGetsUnloaded
     */
    private void checkEmptyRoomAndScheduleUnload(@NonNull Room room) {
        if (!room.isEmpty()) return;

        taskScheduler.schedule(() -> {
            // Check again if the room is empty
            if (!room.isEmpty()) return;

            unloadRoom(room);

        }, new Date(System.currentTimeMillis() + SecondsBeforeEmptyRoomGetsUnloaded * 1000));
    }

    /**
     * Stop the room's task and remove it from active rooms.
     *
     * @param room room to unload
     */
    private void unloadRoom(@NonNull Room room) {
        // Remove the room from activeRoomsTasks
        ScheduledFuture<?> task = activeRoomsTasks.remove(room.getId());

        activeRooms.remove(room.getId());

        // Stop the room's task
        if (task != null && task.cancel(true)) {
            log.debug("Task stopped for room "+ room.getId());
        }
    }

    /**
     * Returns the room with the specified id, or null if it doesn't exist.
     * If the room is active, it retrieves the room from active rooms otherwise from the repository.
     *
     * @param id room id
     * @return the room with the specified id, or null if it doesn't exist.
     * @see #getActiveRoomById
     */
    public Room getRoomById(int id) {
        return activeRooms.containsKey(id)
                ? getActiveRoomById(id)
                : roomRepository.findById(id).orElse(null);
    }

    /**
     * Returns the active room with the specified id, or null if it doesn't exist.
     *
     * @param id active room id
     * @return the active room with the specified id, or null if it doesn't exist.
     */
    public Room getActiveRoomById(int id) {
        return activeRooms.get(id);
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

    /**
     * Check if a room with the given id exists.
     *
     * @param id room's id
     * @return true if the room exists.
     */
    public boolean existsWithId(int id) {
        return roomRepository.existsById(id);
    }
}
