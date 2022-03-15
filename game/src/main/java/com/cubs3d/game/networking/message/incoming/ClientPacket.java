package com.cubs3d.game.networking.message.incoming;

import com.cubs3d.game.networking.message.WebSocketJsonPacket;
import com.cubs3d.game.utils.ApplicationContextUtils;
import org.springframework.context.ApplicationContext;

public abstract class ClientPacket extends WebSocketJsonPacket {

    public abstract void handle();

    protected <T> T getBean(String name, Class<T> clazz) {
        ApplicationContext appCtx = ApplicationContextUtils.getApplicationContext();
        return appCtx.getBean(name, clazz);
    }

    protected <T> T getBean(Class<T> clazz) {
        ApplicationContext appCtx = ApplicationContextUtils.getApplicationContext();
        return appCtx.getBean(clazz);
    }
}
