package com.nhg.game.domain.room;


import com.nhg.game.domain.room.entity.Entity;
import com.nhg.game.domain.room.entity.EntityMap;
import com.nhg.game.domain.room.layout.RoomLayout;
import com.nhg.game.domain.shared.position.Rotation;
import com.nhg.game.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Room implements Runnable {

    private Integer id;
    private String name;
    private String description;
    private User owner;
    private RoomLayout roomLayout;

    private final EntityMap entities;

    public Room() {
        this.entities = new EntityMap();
    }

    public Room(String name, String description, User owner, String layout, int doorX, int doorY, Rotation doorRotation) {
        this();

        this.name = name;
        this.description = description;
        this.owner = owner;
        this.roomLayout = new RoomLayout(layout, doorX, doorY, doorRotation);
    }

    /**
     * Check if the room is empty.
     * The room is empty when it has 0 users inside.
     *
     * @return true if it's empty, false otherwise.
     */
    public boolean isEmpty() {
        return !entities.hasUsers();
    }

    /**
     * Add the given entity to the room's entities.
     *
     * @param entity the entity that needs to be added.
     */
    public void addEntity(@NonNull Entity entity) {
        entities.add(entity);
    }

    /**
     * Remove the given entity from the room's entities and notify all the users.
     *
     * @param entity the entity that needs to be removed.
     */
    public void removeEntity(@NonNull Entity entity) {
        entities.remove(entity);
    }

    @Override
    public void run() {
        synchronized(entities) {
            for (Entity entity : entities) {
                if (entity == null) continue;

                entity.cycle();
            }
        }
    }
}
