package com.nhg.game.infrastructure.networking.packet;

import com.nhg.game.infrastructure.networking.Client;

public interface PacketHandler<Header, Body> {

    /**
     * Handle the packet with the specified header and body sent by the specified client.
     *
     * @param client the client who sent the packet
     * @param header received packet's header
     * @param body received packet's body
     */
    void handle(Client<?> client, Header header, Body body);

}
