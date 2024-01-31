package com.nhg.game.application.usecase.room;

import com.nhg.common.domain.UseCase;
import com.nhg.game.application.repository.ActiveRoomRepository;
import com.nhg.game.application.repository.RoomRepository;
import com.nhg.game.domain.room.Room;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@UseCase
@RequiredArgsConstructor
public class FindRoomUseCase {

    private final RoomRepository roomRepository;
    private final ActiveRoomRepository activeRoomRepository;

    public Optional<Room> byId(int id) {
        return roomRepository.findById(id);
    }

    public Optional<Room> activeById(int id) {
        return activeRoomRepository.findById(id);
    }
}
