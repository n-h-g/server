package com.nhg.game.item;

import com.nhg.game.networking.message.outgoing.JsonSerializable;
import com.nhg.game.room.Room;
import com.nhg.game.room.entity.Entity;
import com.nhg.game.user.User;
import com.nhg.game.utils.Rotation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import com.nhg.game.shared.PersistentPosition;

import javax.persistence.Column;
import javax.persistence.Embedded;
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
@NoArgsConstructor
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
    @JoinColumn(columnDefinition="integer", name="owner_id")
    private User owner;

    @Column(columnDefinition = "integer default 0")
    private Rotation rotation;

    @Embedded
    private PersistentPosition position;

    @ManyToOne
    @JoinColumn(columnDefinition="integer", name="item_specification_id")
    private ItemSpecification itemSpecification;

    @Transient
    private Entity entity;

    public Item(@NonNull ItemSpecification itemSpecification, @NonNull User owner) {
        this.itemSpecification = itemSpecification;
        this.owner = owner;
    }

    @Override
    public JSONObject toJson() throws JSONException {
        return new JSONObject()
                .put("id", Id)
                .put("name", itemSpecification.getName())
                .put("x", position.getX())
                .put("y", position.getY())
                .put("z", position.getZ())
                .put("room_id", room != null ? room.getId() : "-1")
                .put("item_type", itemSpecification.getItemType().getValue());
    }
}
