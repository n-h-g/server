package com.nhg.game.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nhg.game.item.Item;
import com.nhg.game.networking.Client;
import com.nhg.game.networking.message.outgoing.JsonSerializable;
import com.nhg.game.room.Room;
import com.nhg.game.room.entity.Entity;
import com.nhg.game.utils.Gender;
import com.nhg.game.utils.PostgreSQLEnumType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.json.JSONException;
import org.json.JSONObject;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

@Getter
@Setter
@javax.persistence.Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(UserListener.class)
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

    @Column(columnDefinition = "varchar(255) default 'Welcome to NHG'")
    private String motto;

    @Column(columnDefinition = "integer default 0")
    private int credits;

    /**
     * Rooms owned by the user
     */
    @JsonIgnore
    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    private List<Room> rooms;

    @JsonIgnore
    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    private List<Item> items;

    @Transient
    private Client client;

    @Transient
    private Entity entity;

    @Transient
    private boolean isOnline;

    @Override
    public JSONObject toJson() throws JSONException {
        return new JSONObject()
                .put("id", this.id)
                .put("username", this.username)
                .put("gender", this.gender)
                .put("look", this.look)
                .put("online", this.isOnline)
                .put("motto", this.motto)
                .put("credits", this.credits);


    }

    public User(String username, String motto, String look) {
        this.username = username;
        this.motto = motto;
        this.look = look;
    }
}
