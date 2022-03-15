package com.cubs3d.game.networking.message.outgoing;

import com.cubs3d.game.networking.message.WebSocketJsonPacket;
import org.json.JSONObject;


public abstract class ServerPacket extends WebSocketJsonPacket {

    protected ServerPacket() {
        this.body = new JSONObject();
    }
}

