package com.nhg.game.application.usecase.room.entity;

import com.nhg.common.domain.UseCase;
import com.nhg.game.domain.room.entity.Entity;
import com.nhg.game.domain.room.entity.component.ComponentType;
import com.nhg.game.domain.room.entity.component.MovementComponent;
import com.nhg.game.domain.shared.position.Position2;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class EntityMoveToUseCase {

    public void moveTo(@NonNull Entity entity, @NonNull Position2 destination) {
        MovementComponent mc = (MovementComponent) entity.getComponent(ComponentType.Movement);

        mc.setDestination(destination);
        mc.calculatePath();
    }
}
