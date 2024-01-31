package com.nhg.game.infrastructure.networking;

import lombok.NonNull;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ClientMap<ClientId> implements ClientRepository<ClientId> {
    private final Map<ClientId, Client<ClientId>> clients = new ConcurrentHashMap<>();

    @Override
    public void add(@NonNull Client<ClientId> client) {
        clients.put(client.getId(), client);
    }

    @Override
    public void remove(@NonNull ClientId clientId) {
        Client<?> client = clients.remove(clientId);
        client.disconnect();
    }

    @Override
    public Client<ClientId> get(@NonNull ClientId clientId) {
        return clients.get(clientId);
    }
}
