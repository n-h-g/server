package com.nhg.game.domain.room.entity.component;


import com.nhg.game.application.event.room.UpdateRoomEntityEvent;
import com.nhg.game.domain.room.entity.Action;
import com.nhg.game.domain.room.layout.Tile;
import com.nhg.game.domain.room.pathfinding.AStar;
import com.nhg.game.domain.room.pathfinding.Pathfinder;
import com.nhg.game.domain.shared.position.Position2;
import com.nhg.game.domain.shared.position.Position3;
import com.nhg.game.domain.shared.position.Rotation;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.Queue;

@Slf4j
public class MovementComponent extends Component {

    @Getter
    @Setter
    private Position2 destination;

    private final Pathfinder pathfinder;
    private Queue<Tile> calculatedPath;


    public MovementComponent() {
        this.calculatedPath = new LinkedList<>();
        this.pathfinder = new AStar(true);

        this.destination = new Position2(0, 0);
    }

    public PositionComponent getPositionComponent() throws ComponentNotFoundException {
        PositionComponent positionComponent = (PositionComponent) entity.getComponent(ComponentType.Position);

        if (positionComponent == null) {
            log.error("PositionComponent not found on entity "+ entity.getId() +" with MovementComponent");
            throw new ComponentNotFoundException();
        }

        return positionComponent;
    }

    public ActionComponent getActionComponent() throws ComponentNotFoundException {
        ActionComponent actionComponent = (ActionComponent) entity.getComponent(ComponentType.Action);

        if (actionComponent == null) {
            log.error("ActionComponent not found on entity "+ entity.getId() +" with MovementComponent");
            throw new ComponentNotFoundException();
        }

        return actionComponent;
    }

    public BodyHeadRotationComponent getBhrComponent() throws ComponentNotFoundException {
        BodyHeadRotationComponent bhrComponent =
                (BodyHeadRotationComponent) entity.getComponent(ComponentType.BodyHeadRotation);

        if (bhrComponent == null) {
            log.error("BodyHeadRotationComponent not found on entity "+ entity.getId() +" with MovementComponent");
            throw new ComponentNotFoundException();
        }

        return bhrComponent;
    }

    /**
     * Calculate the path from <code>position</code> to <code>destination</code>.
     *
     * @see #getPositionComponent
     * @see #destination
     * @see AStar
     */
    public void calculatePath() {
        try {
            Position2 position = getPositionComponent().getPosition().toPosition2();

            calculatedPath = pathfinder.findPath(
                    entity.getRoom().getRoomLayout().getTile(position),
                    entity.getRoom().getRoomLayout().getTile(destination),
                    entity.getRoom().getRoomLayout());

            log.debug("Calculate Path from current " + position + " to destination " + destination);
        } catch (ComponentNotFoundException e) {
            log.error("PositionComponent in required when using MovementComponent: " + e);
        } catch (IndexOutOfBoundsException ignored) {}
    }

    /**
     * Process the entity movement. It's executed every entity cycle.
     *
     * @see #cycle
     * @see #calculatedPath
     */
    @Override
    public void cycle() {
        try {
            ActionComponent actionComponent = getActionComponent();
            BodyHeadRotationComponent bhrComponent = getBhrComponent();
            PositionComponent positionComponent = getPositionComponent();

            if (calculatedPath == null || calculatedPath.isEmpty()) {
                if (actionComponent.hasAction(Action.MOVE)) {
                    actionComponent.removeAction(Action.MOVE);
                    sendUpdate();
                }

                return;
            }

            Position3 nextPosition = calculatedPath.poll().getPosition();
            actionComponent.addAction(Action.MOVE);

            if (bhrComponent != null) {
                bhrComponent.setRotation(
                        Rotation.CalculateRotation(positionComponent.getPosition().toPosition2(), nextPosition.toPosition2()));
            }

            positionComponent.setPosition(nextPosition);

            sendUpdate();
        } catch (ComponentNotFoundException e) {
            log.error("There are require components for MovementComponent that are missing: " + e);
        }
    }

    private void sendUpdate() {
        entity.getEventPublisher().publish(new UpdateRoomEntityEvent(entity));
    }

}