package com.cubs3d.game.networking.message.outgoing.serverpackets;

import com.cubs3d.game.networking.message.outgoing.OutgoingPacketHeaders;
import com.cubs3d.game.networking.message.outgoing.ServerPacket;
import lombok.NonNull;
import org.json.JSONException;

public class TestTokenResponse extends ServerPacket {

    public TestTokenResponse(@NonNull String token) throws JSONException {
        header = OutgoingPacketHeaders.TestTokenResponse;
        body.put("token", token);
    }
}
