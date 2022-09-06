package com.nhg.game.item;

import com.nhg.game.networking.message.outgoing.JsonSerializable;
import com.nhg.game.room.Room;
import com.nhg.game.user.User;
import com.nhg.game.utils.Int3;
import com.nhg.game.utils.PostgreSQLEnumType;
import com.nhg.game.utils.Rotation;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.json.JSONException;
import org.json.JSONObject;

import javax.persistence.*;

@Getter
@Setter
@javax.persistence.Entity
@Slf4j
@TypeDef(
        name = "pgsql_enum",
        typeClass = PostgreSQLEnumType.class
)
@Table(name = "items")
public class Item implements JsonSerializable, Runnable {

    @Id
    @GeneratedValue(
            generator = "sequence_item_id",
            strategy = GenerationType.SEQUENCE
    )
    @SequenceGenerator(
            name = "sequence_item_id",
            sequenceName = "sequence_item_id"
    )

    private Integer Id;

    @ManyToOne
    @JoinColumn(columnDefinition="integer", name="room_id")
    private Room room;

    @ManyToOne
    private User owner;

    private String name;

    private String baseName;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "itemType default 'FLOOR_ITEM'", nullable = false)
    @Type(type = "pgsql_enum")
    private ItemType itemType;

    @Transient
    private Rotation rotation;

    @Column(columnDefinition = "integer default 0")
    private int x;

    @Column(columnDefinition = "integer default 0")
    private int y;

    @Column(columnDefinition = "integer default 0")
    private int z;

    public Item(Room room, User owner) {
        this.room = room;
        this.owner = owner;
    }
    public Item(Room room) {
        this.room = room;
    }
    public Item(User owner) {
        this.owner = owner;
    }

    public Item() {}

    @Override
    public JSONObject toJson() throws JSONException {
        return new JSONObject()
                .put("id", Id)
                .put("name", name)
                .put("baseName", baseName)
                .put("x", x)
                .put("y", y)
                .put("z", z)
                .put("room_id", room != null ? room.getId() : "-1"  )
                .put("item_type", itemType.getValue());
    }

    @Override
    public void run() {

    }


    public Int3 getPosition() {
        return new Int3(x, y, z);
    }

    public void setPosition(Int3 position) {
        this.x = position.getX();
        this.y = position.getY();
        this.z = position.getZ();
    }
}
