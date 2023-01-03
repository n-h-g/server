package com.nhg.game.networking.message.incoming;

import com.nhg.game.networking.message.WebSocketJsonPacket;
import com.nhg.game.utils.ApplicationContextUtils;
import org.springframework.context.ApplicationContext;

public abstract class ClientPacket extends WebSocketJsonPacket {

    public abstract void handle();
}
