package com.nhg.game.application.usecase.room.user;

import com.nhg.common.domain.UseCase;
import com.nhg.game.application.event.GameEventPublisher;
import com.nhg.game.application.event.room.UserExitRoomEvent;
import com.nhg.game.domain.room.Room;
import com.nhg.game.domain.room.entity.Entity;
import com.nhg.game.domain.user.User;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class UserExitRoomUseCase {

    private final GameEventPublisher gameMessagePublisher;


    public Entity userExitRoom(@NonNull User user, @NonNull Room room) {
        Entity entity = room.getUserEntities().get(user.getId());

        gameMessagePublisher.publish(new UserExitRoomEvent(user.getId(), room.getId()));

        room.userExit(user);

        return entity;
    }
}
