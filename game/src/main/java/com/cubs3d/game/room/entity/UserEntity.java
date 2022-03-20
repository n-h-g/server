package com.cubs3d.game.room.entity;

import com.cubs3d.game.room.Room;
import com.cubs3d.game.user.User;
import lombok.Getter;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Represent an entity in a room controlled by a user.
 *
 * @see Entity
 * @see Room
 */
public class UserEntity extends Entity {

    /**
     * The user that control this entity.
     */
    @Getter
    private final User user;

    public UserEntity(Integer id, Room room, User user) {
        super(id, EntityType.HUMAN, user.getUsername(), room);

        this.user = user;
        user.setEntity(this);
    }

    @Override
    public JSONObject toJson() throws JSONException {
        return super.toJson()
                .put("user_id", user.getId())
                .put("look", user.getLook())
                .put("gender", user.getGender());
    }
}
