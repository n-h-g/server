package com.nhg.game.application.usecase.room.user;

import com.nhg.common.domain.UseCase;
import com.nhg.common.domain.event.DomainEventPublisher;
import com.nhg.game.application.event.room.RemovedRoomEntityEvent;
import com.nhg.game.application.event.room.UserExitRoomEvent;
import com.nhg.game.application.exception.ProblemCode;
import com.nhg.game.application.exception.UseCaseException;
import com.nhg.game.application.repository.UserEntityRepository;
import com.nhg.game.domain.room.Room;
import com.nhg.game.domain.room.entity.Entity;
import com.nhg.game.domain.user.User;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class UserExitRoomUseCase {

    private final DomainEventPublisher eventPublisher;
    private final UserEntityRepository userEntityRepository;


    public Entity userExitRoom(@NonNull User user, @NonNull Room room) {
        Entity entity = userEntityRepository.findEntityByUserId(user.getId()).orElseThrow(() ->
                new UseCaseException(ProblemCode.ENTITY_DOES_NOT_EXIST)
        );

        room.removeEntity(entity);
        userEntityRepository.removeEntityByUserId(user.getId());

        eventPublisher.publish(new RemovedRoomEntityEvent(entity));
        eventPublisher.publish(new UserExitRoomEvent(user.getId(), room.getId()));

        return entity;
    }
}
