package com.nhg.game.adapter.in.websocket.packet.exchange;

import com.nhg.game.adapter.in.websocket.ClientUserMap;
import com.nhg.game.adapter.in.websocket.packet.IncomingPacket;
import com.nhg.game.adapter.out.websocket.OutPacketHeaders;
import com.nhg.game.adapter.out.websocket.OutgoingPacket;
import com.nhg.game.infrastructure.context.BeanRetriever;


public class Ping extends IncomingPacket {

    private final ClientUserMap clientUserMap;

    public Ping() {
        clientUserMap = BeanRetriever.get(ClientUserMap.class);
    }

    @Override
    public void handle() {

        boolean doLogin = !clientUserMap.containsClientId(client.getId());

        OutgoingPacket.send(
                client,
                OutPacketHeaders.Pong,
                doLogin
        );
    }
}
