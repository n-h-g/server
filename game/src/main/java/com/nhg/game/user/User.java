package com.nhg.game.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nhg.game.item.Item;
import com.nhg.game.networking.Client;
import com.nhg.game.networking.message.outgoing.JsonSerializable;
import com.nhg.game.room.Room;
import com.nhg.game.room.entity.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.json.JSONException;
import org.json.JSONObject;
import com.nhg.game.shared.HumanData;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EntityListeners;
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

    @Column(columnDefinition = "varchar(255) default 'Welcome to NHG'")
    private String motto;

    @Column(columnDefinition = "integer default 0")
    private int credits;

    @Embedded
    private HumanData humanData;

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

    public User(String username, String motto, String look) {
        this.username = username;
        this.motto = motto;
        this.humanData = new HumanData(look);
    }

    public void addItemToInventory(@NonNull Item item) {
        items.add(item);
    }

    public void removeItemFromInventory(@NonNull Item item) {
        items.remove(item);
    }

    public void updateCredits(int amount) {
        credits += amount;
    }

    @Override
    public JSONObject toJson() throws JSONException {
        return new JSONObject()
                .put("id", this.id)
                .put("username", this.username)
                .put("gender", this.humanData.getGender())
                .put("look", this.humanData.getLook())
                .put("online", this.isOnline)
                .put("motto", this.motto)
                .put("credits", this.credits);


    }
}
