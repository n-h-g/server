package com.cubs3d.game.user;

import com.cubs3d.game.networking.Client;
import com.cubs3d.game.room.Room;
import com.cubs3d.game.room.entity.RoomUserEntity;
import com.cubs3d.game.utils.Gender;
import com.cubs3d.game.utils.PostgreSQLEnumType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TypeDef(
        name = "pgsql_enum",
        typeClass = PostgreSQLEnumType.class
)
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

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "gender default 'MALE'")
    @Type(type = "pgsql_enum")
    private Gender gender;

    @Column(nullable = false, columnDefinition = "varchar(255) default 'hd-185-10.hr-3163-61.ch-3030-92.lg-275-110'")
    private String look;


    /**
     * Rooms owned by the user
     */
    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    private List<Room> rooms;

    @Transient
    private Client client;

    @Transient
    @Getter
    @Setter
    private RoomUserEntity entity;

}
