package com.nhg.game.user;

import com.nhg.game.utils.Gender;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

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

    @ApiOperation(value = "Create a user with given username, motto and look")
    @PostMapping("/create/{username}/{motto}/{look}")
    public void createUser(@PathVariable String username, @PathVariable String motto, @PathVariable String look) {
       User user = new User(username, motto, look);
       userService.createUser(user);
    }
}
