package com.nhg.game.user;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("api/v1/game/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/exists_with_id/{id}")
    public boolean userExists(@PathVariable Integer id) {
        return userService.existsWithId(id);
    }
}
