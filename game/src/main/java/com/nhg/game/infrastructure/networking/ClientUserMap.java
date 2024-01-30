package com.nhg.game.infrastructure.networking;

import com.nhg.game.domain.user.User;
import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ClientUserMap<ClientId> implements Map<ClientId, User> {
    private final Map<ClientId, User> users = new ConcurrentHashMap<>();

    @Override
    public int size() {
        return users.size();
    }

    @Override
    public boolean isEmpty() {
        return users.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return users.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return users.containsValue(value);
    }

    @Override
    public User get(Object key) {
        return users.get(key);
    }

    @Override
    public User put(ClientId key, User value) {
        return users.put(key, value);
    }

    @Override
    public User remove(Object key) {
        return users.remove(key);
    }

    @Override
    public void putAll(@Nonnull Map<? extends ClientId, ? extends User> m) {
        users.putAll(m);
    }

    @Override
    public void clear() {
        users.clear();
    }

    @Override
    @Nonnull
    public Set<ClientId> keySet() {
        return users.keySet();
    }

    @Override
    @Nonnull
    public Collection<User> values() {
        return users.values();
    }

    @Override
    @Nonnull
    public Set<Entry<ClientId, User>> entrySet() {
        return users.entrySet();
    }
}