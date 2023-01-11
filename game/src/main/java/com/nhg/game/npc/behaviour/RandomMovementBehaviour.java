package com.nhg.game.npc.behaviour;

import com.nhg.game.room.entity.Entity;
import com.nhg.game.room.entity.component.ComponentType;
import com.nhg.game.room.entity.component.MovementComponent;
import com.nhg.game.room.entity.component.PositionComponent;
import com.nhg.game.utils.Int2;

import java.util.Random;

public class RandomMovementBehaviour implements Behaviour {

    private static int range = 15;
    private final Random random;

    public RandomMovementBehaviour() {
         this.random = new Random();
    }

    @Override
    public void cycle(Entity entity) {
        PositionComponent positionComponent = (PositionComponent) entity.getComponent(ComponentType.Position);
        MovementComponent movementComponent = (MovementComponent) entity.getComponent(ComponentType.Movement);

        if (positionComponent == null || movementComponent == null) return;

        movementComponent.setDestination(randomDestination(positionComponent.getPosition().ToInt2XY()));
    }

    private Int2 randomDestination(Int2 currentPosition) {
        int randX = random.nextInt(range * 2 + 1) - range;
        int randY = random.nextInt(range * 2 + 1) - range;

        return new Int2(
                currentPosition.getX() + randX,
                currentPosition.getY() + randY
        );
    }
}
