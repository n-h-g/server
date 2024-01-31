package com.nhg.game.application.usecase.room;

import com.nhg.common.domain.UseCase;
import com.nhg.game.application.event.GameEventPublisher;
import com.nhg.game.application.event.room.RoomDeletedEvent;
import com.nhg.game.application.repository.RoomRepository;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class DeleteRoomUseCase {

    private final RoomRepository roomRepository;
    private final GameEventPublisher gameMessagePublisher;

    public void deleteRoom(int roomId) {
        roomRepository.delete(roomId);

        // TODO Retrieve items
        // TODO Get users out

        gameMessagePublisher.publish(new RoomDeletedEvent(roomId));
    }
}
