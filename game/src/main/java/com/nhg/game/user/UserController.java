package com.nhg.game.user;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("api/v1/game/user")
public class UserController {

    private final UserService userService;

    @ApiOperation(value = "Check if a user with id exists")
    @GetMapping("/exists_with_id/{id}")
    public boolean userExists(@PathVariable Integer id) {
        return userService.existsWithId(id);
    }
}
