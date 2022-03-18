package com.cubs3d.game.networking.message.outgoing.serverpackets.rooms.users;

import com.cubs3d.game.networking.message.outgoing.OutgoingPacketHeaders;
import com.cubs3d.game.networking.message.outgoing.ServerPacket;
import com.cubs3d.game.room.Room;
import com.cubs3d.game.user.User;
import lombok.NonNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoadUsersInRoom extends ServerPacket {

    public LoadUsersInRoom(@NonNull Room room) throws JSONException {
        header = OutgoingPacketHeaders.LoadUsersInRoom;

        JSONArray users = new JSONArray();

        for (User user : room.getUsers()) {
            users.put(new JSONObject()
                .put("id", user.getId())
                .put("name", user.getUsername())
                .put("x", user.getEntity().getPosition().getX())
                .put("y", user.getEntity().getPosition().getY())
                .put("z", user.getEntity().getPosition().getZ())
                .put("rot", user.getEntity().getBodyRotation())
                .put("look", user.getLook())
                .put("gender", Character.toString(user.getGender().toChar())
            ));
        }

        body.put("users", users);

    }
}
