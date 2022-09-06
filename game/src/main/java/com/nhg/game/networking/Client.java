package com.nhg.game.networking;

import com.nhg.game.networking.message.Packet;

public interface Client {

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
