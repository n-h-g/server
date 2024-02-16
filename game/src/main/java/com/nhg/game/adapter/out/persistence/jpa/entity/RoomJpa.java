package com.nhg.game.adapter.out.persistence.jpa.entity;

import com.nhg.game.domain.room.Room;
import com.nhg.game.domain.shared.position.Position2;
import com.nhg.game.domain.shared.position.Rotation;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "rooms")
public class RoomJpa {

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
    private UserJpa owner;

    @Column(columnDefinition = "varchar(1000000) default '0'", nullable = false)
    private String layout;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private Integer doorX;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private Integer doorY;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "door_rotation", columnDefinition = "rotation default 'SOUTH'")
    private Rotation doorRotation;

    public Room toRoom() {
        Room room = new Room(name, description, owner.toUser(), layout, new Position2(doorX, doorY), doorRotation);
        room.setId(id);

        return room;
    }

    public static RoomJpa fromDomain(Room room) {
        RoomJpa roomJpa = RoomJpa.builder()
                .name(room.getName())
                .description(room.getDescription())
                .owner(UserJpa.fromDomain(room.getOwner()))
                .layout(room.getRoomLayout().getLayout())
                .doorX(room.getRoomLayout().getDoorPosition().getX())
                .doorY(room.getRoomLayout().getDoorPosition().getY())
                .doorRotation(room.getRoomLayout().getDoorRotation())
                .build();

        if (room.getId() != null) {
            roomJpa.setId(room.getId());
        }

        return roomJpa;
    }

}
