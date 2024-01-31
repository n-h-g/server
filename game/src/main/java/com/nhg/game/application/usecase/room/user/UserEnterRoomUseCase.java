package com.nhg.game.application.usecase.room.user;

import com.nhg.common.domain.UseCase;
import com.nhg.game.application.event.GameEventPublisher;
import com.nhg.game.application.event.room.RoomActivatedEvent;
import com.nhg.game.application.event.room.UserEnterRoomEvent;
import com.nhg.game.application.exception.ProblemCode;
import com.nhg.game.application.exception.UseCaseException;
import com.nhg.game.application.repository.ActiveRoomRepository;
import com.nhg.game.application.repository.RoomRepository;
import com.nhg.game.domain.room.Room;
import com.nhg.game.domain.user.User;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class UserEnterRoomUseCase {

    private final RoomRepository roomRepository;
    private final ActiveRoomRepository activeRoomRepository;
    private final GameEventPublisher gameMessagePublisher;

    public Room userEnterRoom(@NonNull User user, int roomId) throws UseCaseException {
        Room room = activeRoomRepository.find(roomId).orElse(activateRoomAndGet(roomId));

        room.userEnter(user);

        gameMessagePublisher.publish(new UserEnterRoomEvent(user.getId(), roomId));

        return room;
    }

    private Room activateRoomAndGet(int roomId) throws UseCaseException {
        Room room = roomRepository.find(roomId).orElseThrow(() ->
                new UseCaseException(ProblemCode.ROOM_DOES_NOT_EXIST)
        );

        gameMessagePublisher.publish(new RoomActivatedEvent(roomId));

        return room;
    }
}
