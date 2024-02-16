package com.nhg.game.adapter.in.websocket.packet.exchange;

import com.nhg.game.adapter.in.websocket.ClientUserMap;
import com.nhg.game.adapter.in.websocket.IncomingPacket;
import com.nhg.game.adapter.out.websocket.OutPacketHeader;
import com.nhg.game.adapter.out.websocket.OutgoingPacket;
import com.nhg.game.infrastructure.networking.Client;
import com.nhg.game.infrastructure.networking.packet.ClientPacket;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;


@RequiredArgsConstructor
@IncomingPacket(header = 4)
public class Ping implements ClientPacket<JSONObject> {

    private final ClientUserMap clientUserMap;

    @Override
    public void handle(Client<?> client, JSONObject body) {

        boolean doLogin = !clientUserMap.containsClientId(client.getId());

        OutgoingPacket.send(
                client,
                OutPacketHeader.Pong,
                doLogin
        );
    }
}
