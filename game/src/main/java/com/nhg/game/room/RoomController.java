package com.nhg.game.room;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("api/v1/game/rooms")
public class RoomController {

    private final RoomService roomService;

    @ApiOperation(value = "Check if a room with id exists")
    @GetMapping("/exists_with_id/{id}")
    public boolean roomExists(@PathVariable Integer id) {
        return roomService.existsWithId(id);
    }

}
