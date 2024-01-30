package com.nhg.game.infrastructure.networking.websocket;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@ComponentScan
public class WebSocketConfig implements WebSocketConfigurer {

    private final String handlerPath;
    private final WebSocketHandler webSocketHandler;

    public WebSocketConfig(@Value("${websocket.handler.path}") String handlerPath, WebSocketHandler webSocketHandler) {
        this.handlerPath = handlerPath;
        this.webSocketHandler = webSocketHandler;
    }

    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler, handlerPath)
                .setAllowedOrigins("*");
    }


}