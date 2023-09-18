package com.nhg.game.npc.behaviour;

import com.nhg.game.room.entity.Entity;
import com.nhg.game.room.entity.component.ComponentType;
import com.nhg.game.room.entity.component.MovementComponent;
import com.nhg.game.room.entity.component.PositionComponent;
import com.nhg.game.utils.Position2;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

@Slf4j
public class RandomMovementBehaviour implements Behaviour {

    private static final int range = 15;
    private static final long executeEveryMs = 600;
    private final Random random;
    private long lastExecutionTime = 0;

    public RandomMovementBehaviour() {
         this.random = new Random();
    }

    @Override
    public void onCycle(Entity self) {
        long currentTime = System.currentTimeMillis();

        if (currentTime < lastExecutionTime + executeEveryMs) return;

        lastExecutionTime = currentTime;

        PositionComponent positionComponent = (PositionComponent) self.getComponent(ComponentType.Position);
        MovementComponent movementComponent = (MovementComponent) self.getComponent(ComponentType.Movement);

        if (positionComponent == null || movementComponent == null) return;

        movementComponent.setDestination(randomDestination(positionComponent.getPosition().toPosition2()));
        movementComponent.calculatePath();
    }

    private Position2 randomDestination(Position2 currentPosition) {
        int randX = random.nextInt(range * 2 + 1) - range;
        int randY = random.nextInt(range * 2 + 1) - range;

        return new Position2(
                currentPosition.getX() + randX,
                currentPosition.getY() + randY
        );
    }
}
