package com.nhg.game.application.usecase.user;

import com.nhg.common.domain.UseCase;
import com.nhg.game.application.repository.UserRepository;
import com.nhg.game.domain.user.User;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@UseCase
@RequiredArgsConstructor
public class FindUserUseCase {

    private final UserRepository userRepository;

    public Optional<User> byId(int id) {
        return userRepository.findById(id);
    }
}
