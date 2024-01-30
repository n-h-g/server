package com.nhg.game.infrastructure.context;

import org.springframework.context.ApplicationContext;

public final class BeanRetriever {

    private BeanRetriever() {}

    public static <T> T get(String name, Class<T> clazz) {
        ApplicationContext appCtx = ApplicationContextUtils.getApplicationContext();
        return appCtx.getBean(name, clazz);
    }

    public static <T> T get(Class<T> clazz) {
        ApplicationContext appCtx = ApplicationContextUtils.getApplicationContext();
        return appCtx.getBean(clazz);
    }
}
