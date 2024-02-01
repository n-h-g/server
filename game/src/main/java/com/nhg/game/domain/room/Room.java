package com.nhg.game.domain.room;


import com.nhg.game.domain.room.entity.Entity;
import com.nhg.game.domain.room.layout.RoomLayout;
import com.nhg.game.domain.shared.position.Rotation;
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

    private Integer id;
    private String name;
    private String description;
    private User owner;
    private RoomLayout roomLayout;

    private final Map<Integer, User> users;
    private final Map<UUID, Entity> entities;

    public Room() {
        this.users = new ConcurrentHashMap<>();
        this.entities = new ConcurrentHashMap<>();
    }

    public Room(String name, String description, User owner, String layout, int doorX, int doorY, Rotation doorRotation) {
        this();

        this.name = name;
        this.description = description;
        this.owner = owner;
        this.roomLayout = new RoomLayout(layout, doorX, doorY, doorRotation);
    }

    public void userEnter(@NonNull User user) {
        users.put(user.getId(), user);
    }

    public void userExit(@NonNull User user) {
        users.remove(user.getId());
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
    }

    @Override
    public void run() {

    }
}
