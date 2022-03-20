package com.cubs3d.game.room;

import com.cubs3d.game.networking.message.outgoing.JsonSerializable;
import com.cubs3d.game.networking.message.outgoing.OutgoingPacketHeaders;
import com.cubs3d.game.networking.message.outgoing.ServerPacket;
import com.cubs3d.game.room.entity.Entity;
import com.cubs3d.game.room.entity.UserEntity;
import com.cubs3d.game.room.layout.RoomLayout;
import com.cubs3d.game.user.User;
import com.cubs3d.game.user.UserGroup;
import com.cubs3d.game.utils.Int3;
import com.cubs3d.game.utils.Rotation;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Type;
import org.json.JSONException;
import org.json.JSONObject;

import javax.persistence.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Setter
@javax.persistence.Entity
@Slf4j
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

    @Transient
    private RoomLayout roomLayout;

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
        this.entities = new ConcurrentHashMap<>();
        this.entityIds = new AtomicInteger();
    }

    public Room(String name, User owner, String layout) {
        this();

        this.name = name;
        this.owner = owner;

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
        entity.setPosition(new Int3(doorX, doorY, 0));
        entity.setRotation(doorRotation);

        this.entities.putIfAbsent(entityId, entity);


        // TODO: set user position and rotation according to door
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

        users.sendBroadcastMessageExcept(
                new ServerPacket(OutgoingPacketHeaders.RemoveRoomEntity, user.getEntity().getId()), user);

        user.setEntity(null);
    }

    /**
     * Get the number of users in the room.
     *
     * @return the number of users in the room.
     */
    public int usersCount() {
        return this.users.count();
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
                .put("users_count", usersCount());
    }
}
