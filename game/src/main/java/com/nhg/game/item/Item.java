package com.nhg.game.item;

import com.nhg.game.networking.message.outgoing.JsonSerializable;
import com.nhg.game.room.Room;
import com.nhg.game.room.entity.Entity;
import com.nhg.game.user.User;
import com.nhg.game.utils.Int3;
import com.nhg.game.utils.Rotation;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Getter
@Setter
@javax.persistence.Entity
@Slf4j
@Table(name = "items")
public class Item implements JsonSerializable {

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

    @Column(columnDefinition = "integer default 0")
    private Rotation rotation;

    @Column(columnDefinition = "integer default 0")
    private int x;

    @Column(columnDefinition = "integer default 0")
    private int y;

    @Column(columnDefinition = "integer default 0")
    private int z;

    @ManyToOne
    @JoinColumn(columnDefinition="integer", name="item_specification_id")
    private ItemSpecification itemSpecification;

    @Transient
    private Entity entity;

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

    public Int3 getPosition() {
        return new Int3(x,y,z);
    }

    @Override
    public JSONObject toJson() throws JSONException {
        return new JSONObject()
                .put("id", Id)
                .put("name", itemSpecification.getName())
                .put("x", x)
                .put("y", y)
                .put("z", z)
                .put("room_id", room != null ? room.getId() : "-1")
                .put("item_type", itemSpecification.getItemType().getValue());
    }
}
