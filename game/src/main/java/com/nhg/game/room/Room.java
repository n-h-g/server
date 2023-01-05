package com.nhg.game.room;

import com.nhg.game.item.Item;
import com.nhg.game.networking.message.outgoing.JsonSerializable;
import com.nhg.game.networking.message.outgoing.OutgoingPacketHeaders;
import com.nhg.game.networking.message.outgoing.ServerPacket;
import com.nhg.game.room.entity.Entity;
import com.nhg.game.room.entity.EntityFactory;
import com.nhg.game.room.entity.EntityType;
import com.nhg.game.room.entity.component.ComponentType;
import com.nhg.game.user.User;
import com.nhg.game.user.UserGroup;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.data.util.Pair;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Setter
@Slf4j
@EntityListeners(RoomListener.class)
@javax.persistence.Entity
@Table(name = "rooms")
public class Room implements Runnable, JsonSerializable {

    @Id
    @GeneratedValue(
            generator = "sequence_room_id",
            strategy = GenerationType.SEQUENCE
    )
    @SequenceGenerator(
            name = "sequence_room_id",
            sequenceName = "sequence_room_id"
    )
    private Integer id;

    @Column(nullable = false)
    private String name;

    private String description;

    @ManyToOne
    private User owner;

    @OneToMany(mappedBy = "room", fetch = FetchType.EAGER)
    private final List<Item> items;

    @Embedded
    private RoomLayout roomLayout;

    @Transient
    private int usersCount;

    @Transient
    @Setter(AccessLevel.NONE)
    private final UserGroup users;

    @Transient
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private AtomicInteger entityIds;

    @Transient
    @Setter(AccessLevel.NONE)
    private final Map<UUID, Entity> entities;

    public Room() {
        this.users = new UserGroup();
        this.items = new ArrayList<>();
        this.entities = new ConcurrentHashMap<>();
    }

    public Room(String name, User owner, String layout ,int doorX, int doorY, int doorRotation) {
        this();

        this.name = name;
        this.owner = owner;
        this.roomLayout = new RoomLayout(layout,doorX,doorY,doorRotation);
    }

    /**
     * The specified user enters the room and is added to the user group in the room.
     * Then an entity is associated with the user, created and added to the entities' collection.
     *
     * @param user the user entering the room.
     */
    public void userEnter(@NonNull User user) {
        users.add(user);

        Entity entity = EntityFactory.create(EntityType.HUMAN, this);
        entity.addComponent(ComponentType.User, Pair.of(user, User.class));
        entity.addComponent(ComponentType.Name, Pair.of(user.getUsername(), String.class));

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

        Entity entity = user.getEntity();

        if (entity == null) return;

        removeEntity(entity);
        user.setEntity(null);
    }

    public void addEntity(@NonNull Entity entity) {
        this.entities.putIfAbsent(entity.getId(), entity);
    }

    public void removeEntity(@NonNull Entity entity) {
        entities.remove(entity.getId());

        users.sendBroadcastMessage(
                new ServerPacket(OutgoingPacketHeaders.RemoveRoomEntity, entity.getId().toString()));
    }

    /**
     * Check if the room is empty.
     * The room is empty when it has 0 users inside.
     *
     * @return true if it's empty, false otherwise.
     */
    public boolean isEmpty() {
        return users.count() == 0;
    }

    /**
     * Room cycle, the task is started on RoomService.
     *
     * @see RoomService
     * @see Runnable#run
     */
    @Override
    public void run() {
        synchronized(entities) {
            for (Entity entity : entities.values()) {
                if (entity == null) continue;

                entity.cycle();
            }
        }

        synchronized (items) {
            for(Item item : items) {
                if(item == null) continue;

                item.cycle();
            }
        }

    }
    @Override
    public JSONObject toJson() throws JSONException {
        return new JSONObject()
                .put("id", id)
                .put("name", name)
                .put("layout", roomLayout.getLayout())
                .put("owner_id", owner.getId())
                .put("door_x", roomLayout.getDoorX())
                .put("door_y", roomLayout.getDoorY())
                .put("door_rot", roomLayout.getDoorRotation().getValue())
                .put("users_count", usersCount);
    }

}
