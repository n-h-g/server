package com.cubs3d.game.item;

import com.cubs3d.game.room.Room;
import com.cubs3d.game.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Getter
@Setter
@javax.persistence.Entity
@Slf4j
@Table(name = "items")
public class Item {

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
    private Room room;

    @ManyToOne
    private User owner;

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

    public Item() {

    }
}
