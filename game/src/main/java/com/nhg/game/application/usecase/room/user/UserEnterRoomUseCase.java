package com.nhg.game.application.usecase.room.user;

import com.nhg.common.domain.UseCase;
import com.nhg.common.domain.event.DomainEventPublisher;
import com.nhg.game.application.event.room.RoomActivatedEvent;
import com.nhg.game.application.event.room.UserEnterRoomEvent;
import com.nhg.game.application.repository.ActiveRoomRepository;
import com.nhg.game.application.repository.UserEntityRepository;
import com.nhg.game.domain.room.Room;
import com.nhg.game.domain.room.entity.Entity;
import com.nhg.game.domain.user.User;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class UserEnterRoomUseCase {

    private final ActiveRoomRepository activeRoomRepository;
    private final UserEntityRepository userEntityRepository;
    private final DomainEventPublisher eventPublisher;

    public Room userEnterRoom(@NonNull User user, @NonNull Room room) {
        room = getActiveRoom(room);

        Entity entity = Entity.fromUser(user, room, eventPublisher);

        userEntityRepository.addUserEntity(user, entity);
        room.addEntity(entity);

        eventPublisher.publish(new UserEnterRoomEvent(user.getId(), room.getId()));

        return room;
    }

    private Room getActiveRoom(@NonNull Room room) {
        return activeRoomRepository.findById(room.getId()).orElseGet(() -> {

            // The room was not present in the active rooms, activate it and publish the event.
            activeRoomRepository.add(room);
            eventPublisher.publish(new RoomActivatedEvent(room.getId()));

            return room;
        });
    }
}
