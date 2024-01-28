package com.nhg.game.npc.bot;

import com.nhg.game.networking.message.outgoing.JsonSerializable;
import com.nhg.game.room.Room;
import com.nhg.game.room.entity.Entity;
import com.nhg.game.shared.HumanData;
import com.nhg.game.shared.PersistentPosition;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Transient;
import java.util.List;

@Getter
@NoArgsConstructor
@jakarta.persistence.Entity
public class Bot implements JsonSerializable {

    @Id
    @GeneratedValue(
            generator = "sequence_bot_id",
            strategy = GenerationType.SEQUENCE
    )
    @SequenceGenerator(
            name = "sequence_bot_id",
            sequenceName = "sequence_bot_id"
    )
    private Integer id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(columnDefinition="integer", name="room_id")
    private Room room;

    @OneToMany(mappedBy = "bot")
    private List<BotSpeech> speeches;

    @Embedded
    private PersistentPosition position;

    @Embedded
    private HumanData humanData;

    @Transient
    private Entity entity;

    @Override
    public JSONObject toJson() throws JSONException {
        return new JSONObject()
                .put("id", id)
                .put("name", name)
                .put("gender", this.humanData.getGender())
                .put("look", this.humanData.getLook())
                .put("x", position.getX())
                .put("y", position.getY())
                .put("z", position.getZ())
                .put("room_id", room.getId());
    }
}
