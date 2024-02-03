package com.nhg.game.adapter.out.persistence.jpa.item;

import com.nhg.game.adapter.out.persistence.jpa.entity.RoomJpa;
import com.nhg.game.adapter.out.persistence.jpa.entity.UserJpa;
import com.nhg.game.domain.item.Item;
import com.nhg.game.domain.item.RoomItem;
import com.nhg.game.domain.shared.position.Position3;
import com.nhg.game.domain.shared.position.Rotation;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "items")
public class ItemJpa {

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
    private RoomJpa room;

    @ManyToOne
    @JoinColumn(columnDefinition="integer", name="owner_id")
    private UserJpa owner;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "rotation", columnDefinition = "rotation default 'SOUTH'")
    private Rotation rotation;

    @Column(columnDefinition = "integer default 0")
    private int x;

    @Column(columnDefinition = "integer default 0")
    private int y;

    @Column(columnDefinition = "numeric default 0")
    private float z;

    private String extraData;

    @ManyToOne
    @JoinColumn(columnDefinition="integer", name="item_prototype_id")
    private ItemPrototypeJpa prototype;


    public Item toItem() {
        return new Item(id, prototype.toItemPrototype(), owner.toUser());
    }

    public RoomItem toRoomItem() {
        return new RoomItem(
                id,
                prototype.toItemPrototype(),
                owner.toUser(),
                rotation,
                new Position3(x, y, z),
                extraData
        );
    }
}
