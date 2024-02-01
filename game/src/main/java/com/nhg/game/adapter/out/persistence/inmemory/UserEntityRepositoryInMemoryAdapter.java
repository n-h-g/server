package com.nhg.game.adapter.out.persistence.inmemory;

import com.nhg.game.application.repository.UserEntityRepository;
import com.nhg.game.domain.room.entity.Entity;
import com.nhg.game.domain.user.User;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class UserEntityRepositoryInMemoryAdapter implements UserEntityRepository {

    private final Map<Integer, Entity> entities = new ConcurrentHashMap<>();


    @Override
    public void addUserEntity(User user, Entity entity) {
        entities.put(user.getId(), entity);
    }

    @Override
    public Optional<Entity> findEntityByUserId(int userId) {
        return Optional.ofNullable(entities.get(userId));
    }

    @Override
    public void removeEntityByUserId(int userId) {
        entities.remove(userId);
    }
}
