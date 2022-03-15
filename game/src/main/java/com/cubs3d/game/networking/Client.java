package com.cubs3d.game.networking;

import com.cubs3d.game.networking.message.Packet;

public interface Client {

    /**
     * Send a message to this client.
     *
     * @param packet packet that contains the message.
     */
    void SendMessage(Packet<?,?> packet);

    /**
     * Disconnect this client.
     */
    void disconnect();

}
