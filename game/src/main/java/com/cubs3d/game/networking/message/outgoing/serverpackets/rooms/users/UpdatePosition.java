package com.cubs3d.game.networking.message.outgoing.serverpackets.rooms.users;

import com.cubs3d.game.networking.message.outgoing.OutgoingPacketHeaders;
import com.cubs3d.game.networking.message.outgoing.ServerPacket;
import com.cubs3d.game.utils.Int2;
import com.cubs3d.game.utils.Rotation;
import org.json.JSONException;

public class UpdatePosition extends ServerPacket {
    public UpdatePosition(Int2 position, Rotation rotation) throws JSONException {
        header = OutgoingPacketHeaders.UpdatePosition;

        body.put("x", position.getX());
        body.put("y", position.getY());
        body.put("rot", rotation.getValue());
    }
}
