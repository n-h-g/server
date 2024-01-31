package com.nhg.game.application.usecase.room;

import com.nhg.common.domain.UseCase;
import com.nhg.game.application.dto.CreateRoomRequest;
import com.nhg.game.application.repository.RoomRepository;
import com.nhg.game.domain.room.Room;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class CreateRoomUseCase {

    private final RoomRepository roomRepository;

    public Room createRoom(CreateRoomRequest request) {
        // TODO validation
        Room room = new Room(
                request.name(),
                request.description(),
                request.owner(),
                request.layout(),
                request.doorX(),
                request.doorY(),
                request.doorDirection()
        );

        room = roomRepository.save(room);

        return room;
    }
}
