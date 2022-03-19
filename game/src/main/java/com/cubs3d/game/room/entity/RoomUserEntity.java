package com.cubs3d.game.room.entity;

import com.cubs3d.game.networking.message.outgoing.serverpackets.rooms.entities.UpdateEntity;
import com.cubs3d.game.room.Room;
import com.cubs3d.game.user.User;
import lombok.Getter;
import org.json.JSONException;
import org.json.JSONObject;

public class RoomUserEntity extends RoomEntity {

    @Getter
    private final User user;

    public RoomUserEntity(Integer id, Room room, User user) {
        super(id, user.getUsername(), room);

        this.user = user;
        user.setEntity(this);
    }

    @Override
    protected void onPositionSet() {
        try {
            getRoom().getUsers().sendBroadcastMessage(new UpdateEntity(this));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public JSONObject toJsonObject() throws JSONException {
        return super.toJsonObject()
                .put("user_id", user.getId())
                .put("look", user.getLook())
                .put("gender", user.getGender());
    }
}
