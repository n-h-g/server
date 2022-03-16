package com.cubs3d.game.networking.message.outgoing.serverpackets.rooms.users;

import com.cubs3d.game.networking.message.outgoing.OutgoingPacketHeaders;
import com.cubs3d.game.networking.message.outgoing.ServerPacket;
import com.cubs3d.game.user.User;
import org.json.JSONException;

public class NewRoomUser extends ServerPacket {

    public NewRoomUser(User user) throws JSONException {
        header = OutgoingPacketHeaders.NewRoomUser;

        body.put("id", user.getId());
        body.put("name", user.getUsername());
        body.put("x", 1);
        body.put("y", 1);
        body.put("z", 1);
        body.put("rot", 1);
        body.put("look", 1);
        body.put("gender", user.getGender());

    }
}
