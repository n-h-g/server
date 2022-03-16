package com.cubs3d.game.networking;

import com.cubs3d.game.networking.message.Packet;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class Clients {

    private final Map<String, Client> clients = new ConcurrentHashMap<>();

    public void add(@NonNull WebSocketSession session) {
        clients.putIfAbsent(session.getId(), new WebSocketClient(session));
    }

    public void remove(@NonNull String clientId) {
        Client client = clients.remove(clientId);

        if (client == null) return;

        client.disconnect();
    }

    public Client get(@NonNull String clientId) {
        return clients.get(clientId);
    }

    public void SendBroadcastMessage(@NonNull Packet<?,?> packet) {
        for (Client client : clients.values()) {
            client.SendMessage(packet);
        }
    }

}
