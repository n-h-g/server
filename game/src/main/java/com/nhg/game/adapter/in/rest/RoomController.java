package com.nhg.game.adapter.in.rest;

import com.nhg.game.application.dto.RoomResponse;
import com.nhg.game.application.usecase.room.FindRoomUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/game/rooms")
public class RoomController {

    private final FindRoomUseCase findRoomUseCase;


    @GetMapping("/{id}")
    public RoomResponse getRoom(@PathVariable Integer id) {
        return findRoomUseCase.byId(id)
                .map(RoomResponse::fromDomain)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found"));
    }
}