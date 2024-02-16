package com.nhg.game.infrastructure.networking;

import com.nhg.game.infrastructure.networking.packet.ServerPacket;

public interface Client<ClientId> {

    ClientId getId();

    /**
     * Send a message to this client.
     *
     * @param packet packet that contains the message.
     */
    void sendMessage(ServerPacket<?,?> packet);

    /**
     * Disconnect this client.
     */
    void disconnect();

}
