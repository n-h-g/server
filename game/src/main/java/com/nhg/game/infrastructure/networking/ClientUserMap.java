package com.nhg.game.infrastructure.networking;

import com.nhg.game.domain.user.User;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class ClientUserMap {

    private final ClientRepository clientRepository;
    private final Map<Object, User> users = new ConcurrentHashMap<>();
    private final Map<Integer, Object> clientIds = new ConcurrentHashMap<>();


    public int size() {
        return users.size();
    }

    public boolean isEmpty() {
        return users.isEmpty();
    }

    public boolean containsClientId(@NonNull Object clientId) {
        return users.containsKey(clientId);
    }
    public boolean containsUserId(@NonNull Integer userId) {
        return clientIds.containsKey(userId);
    }

    public User getUser(Object clientId) {
        return users.get(clientId);
    }

    @SuppressWarnings("unchecked")
    public Client getClient(@NonNull Integer userId) {
        Object clientId = clientIds.get(userId);

        return clientRepository.get(clientId);
    }

    public void put(@NonNull Object clientId, @NonNull User user) {
        clientIds.put(user.getId(), clientId);
        users.put(clientId, user);
    }

    public void remove(@NonNull Object clientId) {
        User user = users.remove(clientId);

        if (user == null) return;

        clientIds.remove(user.getId());
    }


    public void clear() {
        clientIds.clear();
        users.clear();
    }
}