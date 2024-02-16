package com.nhg.game.infrastructure.networking.packet;


import com.nhg.game.infrastructure.networking.Client;

public interface ClientPacket<Body> {

    void handle(Client<?> client, Body body);

}
