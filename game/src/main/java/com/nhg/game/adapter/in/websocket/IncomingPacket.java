package com.nhg.game.adapter.in.websocket;

import com.nhg.game.adapter.in.websocket.exchange.Handshake;
import com.nhg.game.adapter.in.websocket.exchange.Ping;
import com.nhg.game.infrastructure.networking.Client;
import com.nhg.game.infrastructure.networking.packet.ClientPacket;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.JSONObject;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class IncomingPacket implements ClientPacket<Integer, JSONObject> {

    protected Integer header;
    protected JSONObject body;
    protected Client<?> client;

    @Override
    public String toString() {
        return "{\"header\": " + this.getHeader() + ", \"body\": " + this.body.toString() + "}";
    }

    public static final Map<Integer, Class<? extends ClientPacket<Integer, JSONObject>>> HeaderClassMap =
            Map.ofEntries(
                    Map.entry(1, Handshake.class),
                    Map.entry(4, Ping.class)
            );
}
