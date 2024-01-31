package com.nhg.game.application.usecase.room.user;

import com.nhg.common.domain.UseCase;
import com.nhg.game.application.event.GameEventPublisher;
import com.nhg.game.application.event.room.RoomActivatedEvent;
import com.nhg.game.application.event.room.UserEnterRoomEvent;
import com.nhg.game.application.exception.UseCaseException;
import com.nhg.game.application.repository.ActiveRoomRepository;
import com.nhg.game.domain.room.Room;
import com.nhg.game.domain.user.User;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class UserEnterRoomUseCase {

    private final ActiveRoomRepository activeRoomRepository;
    private final GameEventPublisher gameMessagePublisher;

    public Room userEnterRoom(@NonNull User user, @NonNull Room room) throws UseCaseException {
        room = getActiveRoom(room);

        room.userEnter(user);

        gameMessagePublisher.publish(new UserEnterRoomEvent(user.getId(), room.getId()));

        return room;
    }

    private Room getActiveRoom(@NonNull Room room) throws UseCaseException {
        return activeRoomRepository.findById(room.getId()).orElseGet(() -> {

            // The room was not present in the active rooms, activate it and publish the event.
            activeRoomRepository.add(room);
            gameMessagePublisher.publish(new RoomActivatedEvent(room.getId()));

            return room;
        });
    }
}
