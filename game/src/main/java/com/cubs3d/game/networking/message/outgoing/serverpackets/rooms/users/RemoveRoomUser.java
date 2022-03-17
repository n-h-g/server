package com.cubs3d.game.networking.message.outgoing.serverpackets.rooms.users;

import com.cubs3d.game.networking.message.outgoing.OutgoingPacketHeaders;
import com.cubs3d.game.networking.message.outgoing.ServerPacket;
import com.cubs3d.game.user.User;
import lombok.NonNull;
import org.json.JSONException;

public class RemoveRoomUser extends ServerPacket {

    public RemoveRoomUser(@NonNull User user) throws JSONException {
        header = OutgoingPacketHeaders.RemoveRoomUser;

        body.put("id", user.getId());
    }
}
