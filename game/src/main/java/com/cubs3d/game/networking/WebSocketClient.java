package com.cubs3d.game.networking;

import com.cubs3d.game.networking.message.Packet;
import com.cubs3d.game.user.User;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

public class WebSocketClient implements Client {

    private final WebSocketSession session;

    @Getter @Setter
    private User user;

    public WebSocketClient(@NonNull WebSocketSession session) {
        this.session = session;
    }

    @Override
    public void SendMessage(@NonNull Packet<?,?> packet) {
        try {
            session.sendMessage(new TextMessage(packet.toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void disconnect() {
        try {
            session.close();
            this.unlinkUser();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void linkUser(@NonNull User user) {
        user.setClient(this);
        this.user = user;
    }

    private void unlinkUser() {
        if (user == null) return;
        user.setClient(null);
        user = null;
    }

}
