package com.cubs3d.game.user;

import com.cubs3d.game.item.Item;
import com.cubs3d.game.networking.Client;
import com.cubs3d.game.networking.message.outgoing.JsonSerializable;
import com.cubs3d.game.room.Room;
import com.cubs3d.game.room.entity.UserEntity;
import com.cubs3d.game.utils.Gender;
import com.cubs3d.game.utils.PostgreSQLEnumType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.json.JSONException;
import org.json.JSONObject;

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
public class User implements JsonSerializable {

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

    @Column(nullable = false, columnDefinition = "varchar(255) default 'hd-180-1.ch-255-66.lg-280-110.sh-305-62.ha-1012-110.hr-828-61'")
    private String look;

    /**
     * Rooms owned by the user
     */
    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    private List<Room> rooms;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    private List<Item> items;

    @Transient
    private Client client;

    @Transient
    @Getter
    @Setter
    private UserEntity entity;

    @Column(name = "online", nullable = false, columnDefinition = "boolean default true")
    private boolean online;

    @Override
    public JSONObject toJson() throws JSONException {
        return new JSONObject()
                .put("id", this.id)
                .put("username", this.username)
                .put("gender", this.gender)
                .put("look", this.look)
                .put("online", this.online);

    }
}
