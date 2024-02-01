package com.nhg.game.infrastructure.networking.packet;


import com.nhg.game.infrastructure.networking.Client;

public interface ClientPacket<Header, Body> extends Packet<Header, Body> {

    void setClient(Client<?> client);
    void handle() throws Exception;
}
