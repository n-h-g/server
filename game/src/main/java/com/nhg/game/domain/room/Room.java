package com.nhg.game.domain.room;


import com.nhg.game.domain.room.entity.Entity;
import com.nhg.game.domain.room.layout.RoomLayout;
import com.nhg.game.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Setter
@AllArgsConstructor
public class Room implements Runnable {

    private int id;
    private String name;
    private String description;
    private User owner;
    private RoomLayout roomLayout;

    private final Map<Integer, User> users;
    private final Map<Integer, Entity> userEntities;
    private final Map<UUID, Entity> entities;

    public Room() {
        this.users = new ConcurrentHashMap<>();
        this.userEntities = new ConcurrentHashMap<>();
        this.entities = new ConcurrentHashMap<>();
    }

    public Room(String name, String description, User owner, String layout, int doorX, int doorY, int doorRotation) {
        this();

        this.name = name;
        this.description = description;
        this.owner = owner;
        this.roomLayout = new RoomLayout(layout, doorX, doorY, doorRotation);
    }

    /**
     * The specified user enters the room and is added to the user group in the room.
     * Then an entity is associated with the user, created and added to the entities' collection.
     *
     * @param user the user entering the room.
     */
    public void userEnter(@NonNull User user) {
        if (userEntities.containsKey(user.getId())) return;

        users.put(user.getId(), user);

        Entity entity = Entity.fromUser(user, this);
        userEntities.put(user.getId(), entity);

        addEntity(entity);
    }

    /**
     * The specified user exits the room and is removed from the user group in the room.
     * If the user has an entity in the room it's removed and all the other users are notified.
     *
     * @param user the user exiting the room.
     */
    public void userExit(@NonNull User user) {
        users.remove(user.getId());

        Entity entity = userEntities.remove(user.getId());

        removeEntity(entity);
    }

    /**
     * Check if the room is empty.
     * The room is empty when it has 0 users inside.
     *
     * @return true if it's empty, false otherwise.
     */
    public boolean isEmpty() {
        return users.isEmpty();
    }

    /**
     * Add the given entity to the room's entities.
     *
     * @param entity the entity that needs to be added.
     */
    public void addEntity(@NonNull Entity entity) {
        this.entities.putIfAbsent(entity.getId(), entity);
    }

    /**
     * Remove the given entity from the room's entities and notify all the users.
     *
     * @param entity the entity that needs to be removed.
     */
    public void removeEntity(@NonNull Entity entity) {
        entities.remove(entity.getId());

        // TODO event (use case)
    }

    @Override
    public void run() {

    }
}
