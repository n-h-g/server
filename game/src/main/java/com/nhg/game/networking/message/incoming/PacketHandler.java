package com.nhg.game.networking.message.incoming;

import com.nhg.game.networking.Client;

public interface PacketHandler<Header, Body> {

    /**
     * Handle the packet with the specified header and body sent by the specified client.
     *
     * @param client the client who sent the packet
     * @param header received packet's header
     * @param body received packet's body
     */
    void handle(Client client, Header header, Body body);

}
