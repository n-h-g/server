package com.cubs3d.game.networking.message.outgoing.serverpackets.handshake;

import com.cubs3d.game.networking.message.outgoing.OutgoingPacketHeaders;
import com.cubs3d.game.networking.message.outgoing.ServerPacket;
import lombok.NonNull;
import org.json.JSONException;

public class PongResponse extends ServerPacket {

    public PongResponse(boolean message) throws JSONException {
        header = OutgoingPacketHeaders.PongResponse;

        body.put("login", message);
    }
}
