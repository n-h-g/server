package com.nhg.game.user;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("api/v1/game/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/exists_with_id/{id}")
    public boolean userExists(@PathVariable Integer id) {
        return userService.existsWithId(id);
    }
}
