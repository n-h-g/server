package com.nhg.game.infrastructure.networking.websocket;

import com.nhg.game.infrastructure.networking.Client;
import com.nhg.game.infrastructure.networking.packet.ServerPacket;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

@Slf4j
public class WebSocketClient implements Client<String> {

    private final WebSocketSession session;

    public WebSocketClient(WebSocketSession session) {
        this.session = session;
    }

    @Override
    public String getId() {
        return session.getId();
    }

    @Override
    public void sendMessage(@NonNull ServerPacket<?,?> packet) {
        try {
            session.sendMessage(new TextMessage(packet.toString()));
        } catch (IOException e) {
            log.error(String.format("Error while sending message to client %s, message error: %s",
                    session.getId(), e.getMessage()));
        }
    }

    @Override
    public void disconnect() {
        try {
            session.close();
        } catch (IOException e) {
            log.error(String.format("Error while disconnecting client %s, message error: %s",
                    session.getId(), e.getMessage()));
        }
    }
}