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


    /**
     * Add a new client using the given <code>WebSocketSession</code>
     *
     * @param session session associated with the client.
     * @see WebSocketSession
     * @see WebSocketClient
     */
    public void add(@NonNull WebSocketSession session) {
        clients.putIfAbsent(session.getId(), new WebSocketClient(session));
    }

    /**
     * Remove and disconnect the client with the given id.
     *
     * @param clientId client's id.
     * @see Client#disconnect
     */
    public void remove(@NonNull String clientId) {
        Client client = clients.remove(clientId);

        if (client == null) return;

        client.disconnect();
    }

    /**
     * Get the client with the given id.
     *
     * @param clientId client's id.
     * @return the client if it exists, null otherwise.
     */
    public Client get(@NonNull String clientId) {
        return clients.get(clientId);
    }

    /**
     * Send a message to all clients.
     *
     * @param packet the package to be sent.
     * @see Client#sendMessage
     */
    public void SendBroadcastMessage(@NonNull Packet<?,?> packet) {
        for (Client client : clients.values()) {
            client.sendMessage(packet);
        }
    }

}
