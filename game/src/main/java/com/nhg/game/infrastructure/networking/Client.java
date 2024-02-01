package com.nhg.game.infrastructure.networking;

import com.nhg.game.infrastructure.networking.packet.Packet;

public interface Client<ClientId> {

    ClientId getId();

    /**
     * Send a message to this client.
     *
     * @param packet packet that contains the message.
     */
    void sendMessage(Packet<?,?> packet);

    /**
     * Disconnect this client.
     */
    void disconnect();

}
