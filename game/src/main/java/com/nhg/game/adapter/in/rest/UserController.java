package com.nhg.game.adapter.in.rest;

import com.nhg.game.application.dto.UserResponse;
import com.nhg.game.application.usecase.user.FindUserUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/game/users")
public class UserController {

    private final FindUserUseCase findUserUseCase;


    @GetMapping("/{id}")
    public UserResponse getUser(@PathVariable Integer id) {
        return findUserUseCase.byId(id)
                .map(UserResponse::fromDomain)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }
}
