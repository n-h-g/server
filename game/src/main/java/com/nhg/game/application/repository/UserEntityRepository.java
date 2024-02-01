package com.nhg.game.application.repository;

import com.nhg.game.domain.room.entity.Entity;
import com.nhg.game.domain.user.User;

import java.util.Optional;

public interface UserEntityRepository {

    void addUserEntity(User user, Entity entity);

    Optional<Entity> findEntityByUserId(int userId);

    void removeEntityByUserId(int userId);
}
