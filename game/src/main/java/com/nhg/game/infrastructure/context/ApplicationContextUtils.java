package com.nhg.game.infrastructure.context;

import lombok.NonNull;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextUtils implements ApplicationContextAware {

    private static ApplicationContext ctx;

    @Override
    public void setApplicationContext(@NonNull ApplicationContext appContext) {
        ctx = appContext;
    }

    public static ApplicationContext getApplicationContext() {
        return ctx;
    }
}