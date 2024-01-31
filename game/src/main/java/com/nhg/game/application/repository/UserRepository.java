package com.nhg.game.application.repository;

import com.nhg.game.domain.user.User;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findByUsername(String username);
    Optional<User> findById(int id);
    boolean existsById(int id);
}
