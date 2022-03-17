package com.cubs3d.game.networking.message.outgoing.serverpackets.rooms.users;

import com.cubs3d.game.networking.message.outgoing.OutgoingPacketHeaders;
import com.cubs3d.game.networking.message.outgoing.ServerPacket;
import com.cubs3d.game.room.Room;
import com.cubs3d.game.user.User;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoadUsersInRoom extends ServerPacket {

    public LoadUsersInRoom(Room room) throws JSONException {
        header = OutgoingPacketHeaders.LoadUsersInRoom;

        JSONArray users = new JSONArray();

        for (User user : room.getUsers()) {
            users.put(new JSONObject()
                .put("id", user.getId())
                .put("name", user.getUsername())
                .put("x", 1)
                .put("y", 1)
                .put("z", 1)
                .put("rot", 1)
                .put("look", user.getLook())
                .put("gender", Character.toString(user.getGender().toChar())
            ));
        }

        body.put("users", users);

    }
}
