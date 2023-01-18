package com.nhg.game.networking.message.incoming;

import com.nhg.game.networking.message.WebSocketJsonPacket;

public abstract class ClientPacket extends WebSocketJsonPacket {

    public abstract void handle() throws Exception;
}
