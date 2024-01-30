package com.nhg.game.infrastructure.networking;


public interface ClientPacket<Header, Body> extends Packet<Header, Body> {

    void setClient(Client<?> client);
    void handle() throws Exception;
}
