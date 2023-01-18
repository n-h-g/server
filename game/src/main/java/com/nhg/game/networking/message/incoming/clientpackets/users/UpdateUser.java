package com.nhg.game.networking.message.incoming.clientpackets.users;

import com.nhg.game.networking.Clients;
import com.nhg.game.networking.WebSocketClient;
import com.nhg.game.networking.message.incoming.ClientPacket;
import com.nhg.game.networking.message.outgoing.OutgoingPacketHeaders;
import com.nhg.game.networking.message.outgoing.ServerPacket;
import com.nhg.game.user.User;
import com.nhg.game.utils.BeanRetriever;

public class UpdateUser extends ClientPacket {

    private final Clients clients;

    public UpdateUser() {
        clients = BeanRetriever.get(Clients.class);
    }

    @Override
    public void handle() throws Exception {
        WebSocketClient wsClient = (WebSocketClient) client;
        User user = wsClient.getUser();

        clients.SendBroadcastMessage(new ServerPacket(
                OutgoingPacketHeaders.UpdateUserInformation,
                user
        ));
    }
}
