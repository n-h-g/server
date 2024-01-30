package com.nhg.game.adapter.in.websocket;

import com.nhg.game.adapter.out.websocket.OutPacketHeaders;
import com.nhg.game.infrastructure.context.BeanRetriever;
import com.nhg.game.infrastructure.networking.ClientUserMap;
import com.nhg.game.infrastructure.networking.OutgoingPacket;


public class Ping extends IncomingPacket {

    private final ClientUserMap<String> clientUserMap;

    @SuppressWarnings("unchecked")
    public Ping() {
        clientUserMap = BeanRetriever.get(ClientUserMap.class);
    }

    @Override
    public void handle() {

        boolean doLogin = !clientUserMap.containsKey((String) client.getId());

        client.sendMessage(new OutgoingPacket(OutPacketHeaders.Pong, doLogin));
    }
}
