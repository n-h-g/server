package com.cubs3d.game.networking.message;

import com.cubs3d.game.networking.Client;
import lombok.*;
import org.json.JSONObject;

/**
 * Packet for a WebSocket connection using json to exchange data.
 * Integer are used as packet header and JSONObject for data.
 *
 * @see Packet
 * @see Client
 */
@AllArgsConstructor
@NoArgsConstructor
public abstract class WebSocketJsonPacket implements Packet<Integer, JSONObject> {

    @Getter
    @Setter
    protected Integer header;

    @Getter
    @Setter
    protected JSONObject body;

    @Getter
    @Setter
    protected Client client;

    @Override
    public String toString() {
        return "{\"header\": " + this.getHeader() + ", \"body\": " + this.body.toString() + "}";
    }
}
