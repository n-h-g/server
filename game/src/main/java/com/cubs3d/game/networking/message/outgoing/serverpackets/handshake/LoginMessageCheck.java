package com.cubs3d.game.networking.message.outgoing.serverpackets.handshake;

import com.cubs3d.game.networking.message.outgoing.OutgoingPacketHeaders;
import com.cubs3d.game.networking.message.outgoing.ServerPacket;
import lombok.NonNull;
import org.json.JSONException;

public class LoginMessageCheck extends ServerPacket {

    public LoginMessageCheck(@NonNull boolean success) throws JSONException {
        header = OutgoingPacketHeaders.LoginMessageCheck;

        body.put("success", success);
    }
}
