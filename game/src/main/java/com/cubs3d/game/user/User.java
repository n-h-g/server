package com.cubs3d.game.user;

import com.cubs3d.game.networking.Client;
import com.cubs3d.game.room.Room;
import com.cubs3d.game.room.entity.RoomEntity;
import com.cubs3d.game.utils.Int3;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(
            generator = "sequence_user_id",
            strategy = GenerationType.SEQUENCE
    )
    @SequenceGenerator(
            name = "sequence_user_id",
            sequenceName = "sequence_user_id"
    )
    private Integer id;

    @Column(nullable = false)
    private String username;

    /**
     * Rooms owned by the user
     */
    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    private List<Room> rooms;

    @Transient
    private Client client;

    @Transient
    private RoomEntity entity;

}
