package com.nhg.game.room;

import com.nhg.game.item.Item;
import com.nhg.game.networking.message.outgoing.JsonSerializable;
import com.nhg.game.networking.message.outgoing.OutgoingPacketHeaders;
import com.nhg.game.networking.message.outgoing.ServerPacket;
import com.nhg.game.room.entity.Entity;
import com.nhg.game.room.entity.UserEntity;
import com.nhg.game.room.layout.RoomLayout;
import com.nhg.game.user.User;
import com.nhg.game.user.UserGroup;
import com.nhg.game.utils.Int3;
import com.nhg.game.utils.Rotation;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Type;
import org.json.JSONException;
import org.json.JSONObject;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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

    @Column(nullable = false)
    private String layout;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private Integer doorX;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private Integer doorY;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "rotation default 'SOUTH'")
    @Type(type = "pgsql_enum")
    private Rotation doorRotation;

    @OneToMany(mappedBy = "room", fetch = FetchType.EAGER)
    private final List<Item> items;

    @Transient
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
    private final Map<Integer, Entity> entities;

    public Room() {
        this.users = new UserGroup();
        this.items = new ArrayList<>();
        this.entities = new ConcurrentHashMap<>();
        this.entityIds = new AtomicInteger();
    }

    public Room(String name, User owner, String layout) {
        this();

        this.name = name;
        this.owner = owner;

        this.setLayout(layout);
    }

    public Room(String name, User owner, String layout ,int doorX, int doorY, int doorRotation) {
        this();

        this.name = name;
        this.owner = owner;
        this.doorX = doorX;
        this.doorY = doorY;
        this.doorRotation = Rotation.intToRotation(doorRotation);

        this.setLayout(layout);
    }

    /**
     * The specified user enters the room and is added to the user group in the room.
     * Then an entity is associated with the user, created and added to the entities' collection.
     *
     * @param user the user entering the room.
     */
    public void userEnter(@NonNull User user) {
        users.add(user);

        Integer entityId = this.entityIds.getAndIncrement();

        Entity entity = new UserEntity(entityId, this, user);
        entity.setPosition(new Int3(doorX, doorY, getRoomLayout().getTile(doorX, doorY).getPosition().getZ()));
        entity.setRotation(doorRotation);

        this.entities.putIfAbsent(entityId, entity);
        usersCount++;
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

        entities.remove(entity.getId());

        try {
            users.sendBroadcastMessageExcept(
                    new ServerPacket(OutgoingPacketHeaders.RemoveRoomEntity, new JSONObject().put("id", entity.getId()).put("user_id", user.getId())), user);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        user.setEntity(null);
        usersCount--;
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
     + Place the item in the room and put it into a map.
     *
     * @param item the item placed in the room
     */
    public void addItem(@NonNull Item item) {
        this.items.add(item);
    }

    public Item getItem(@NonNull Integer itemId) {
        for(Item item : items) {
            if(item.getId().equals(itemId)) {
                return item;
            }
        }

        return null;
    }

    /**
     + Remove the item from map
     *
     * @param item the item placed in the room
     */
    public void removeItem(@NonNull Item item) {
       items.removeIf(i -> Objects.equals(i.getId(), item.getId()));
    }

    /**
     * Set layout and create RoomLayout from the new layout.
     *
     * @param layout string representing the layout
     * @see RoomLayout#RoomLayout
     */
    public void setLayout(String layout) {
        this.layout = layout;
        this.roomLayout = new RoomLayout(layout);
    }

    /**
     * Get the <code>RoomLayout</code> class, if it's null it will also create a new instance from <code>layout</code>.
     *
     * @return the RoomLayout
     * @see #layout
     * @see RoomLayout
     */
    public RoomLayout getRoomLayout() {
        if (roomLayout == null) {
            roomLayout = new RoomLayout(layout);
        }
        return roomLayout;
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
                .put("layout", layout)
                .put("owner_id", owner.getId())
                .put("door_x", doorX)
                .put("door_y", doorY)
                .put("door_rot", doorRotation.getValue())
                .put("users_count", usersCount);
    }

    /**
     *  Pick all the items to his owner
     */
    public void emptyItems() {
        for(Item item: items) {
            item.setRoom(null);
        }
        items.clear();
    }

    /**
     *  Clear all objects inside room
     */
    public void dispose() {
       entities.clear();
       users.clear();
    }
}
