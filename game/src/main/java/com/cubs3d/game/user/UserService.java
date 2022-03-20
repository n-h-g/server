package com.cubs3d.game.user;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public User getUserById(int id) { return userRepository.findById(id).orElse(null); }

    public boolean existWithId(int id) {
        return userRepository.existsById(id);
    }

}
