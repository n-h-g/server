package com.cubs3d.game.networking.message.outgoing.serverpackets.rooms.users;

import com.cubs3d.game.networking.message.outgoing.OutgoingPacketHeaders;
import com.cubs3d.game.networking.message.outgoing.ServerPacket;
import com.cubs3d.game.user.User;
import lombok.NonNull;
import org.json.JSONException;

public class NewRoomUser extends ServerPacket {

    public NewRoomUser(@NonNull User user) throws JSONException {
        header = OutgoingPacketHeaders.NewRoomUser;

        body.put("id", user.getId());
        body.put("name", user.getUsername());
        body.put("x", user.getEntity().getPosition().getX());
        body.put("y", user.getEntity().getPosition().getY());
        body.put("z", user.getEntity().getPosition().getZ());
        body.put("rot", user.getEntity().getBodyRotation());
        body.put("look", user.getLook());
        body.put("gender", user.getGender());

    }
}
