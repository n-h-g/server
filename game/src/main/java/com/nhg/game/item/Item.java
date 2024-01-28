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

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Getter
@Setter
@jakarta.persistence.Entity
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
    private Integer id;

    @ManyToOne
    @JoinColumn(columnDefinition="integer", name="room_id")
    private Room room;

    @ManyToOne
    @JoinColumn(columnDefinition="integer", name="owner_id")
    private User owner;

    @Column(columnDefinition = "rotation default 'NORTH'")
    private Rotation rotation;

    @Embedded
    private PersistentPosition position;

    private String extraData;

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
                .put("id", id)
                .put("name", itemSpecification.getName())
                .put("x", position.getX())
                .put("y", position.getY())
                .put("z", position.getZ())
                .put("room_id", room != null ? room.getId() : "-1")
                .put("item_type", itemSpecification.getItemType().getValue());
    }
}
