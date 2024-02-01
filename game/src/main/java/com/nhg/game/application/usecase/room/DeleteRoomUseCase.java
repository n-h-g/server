package com.nhg.game.application.usecase.room;

import com.nhg.common.domain.UseCase;
import com.nhg.common.domain.event.DomainEventPublisher;
import com.nhg.game.application.event.room.RoomDeletedEvent;
import com.nhg.game.application.repository.RoomRepository;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class DeleteRoomUseCase {

    private final RoomRepository roomRepository;
    private final DomainEventPublisher eventPublisher;

    public void deleteRoom(int roomId) {
        roomRepository.delete(roomId);

        // TODO Retrieve items
        // TODO Get users out

        eventPublisher.publish(new RoomDeletedEvent(roomId));
    }
}
