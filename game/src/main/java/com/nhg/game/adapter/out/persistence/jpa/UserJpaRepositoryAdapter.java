package com.nhg.game.adapter.out.persistence.jpa;

import com.nhg.game.adapter.out.persistence.jpa.entity.UserJpa;
import com.nhg.game.adapter.out.persistence.jpa.repository.UserJpaRepository;
import com.nhg.game.application.repository.UserRepository;
import com.nhg.game.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserJpaRepositoryAdapter implements UserRepository {

    private final UserJpaRepository userJpaRepository;


    @Override
    public Optional<User> findByUsername(String username) {
        return userJpaRepository.findByUsername(username)
                .map(UserJpa::toUser);
    }

    @Override
    public Optional<User> findById(int id) {
        return userJpaRepository.findById(id)
                .map(UserJpa::toUser);
    }

    @Override
    public boolean existsById(int id) {
        return userJpaRepository.existsById(id);
    }
}
