package com.nhg.game.room.entity.component;

import com.nhg.game.networking.message.outgoing.OutgoingPacketHeaders;
import com.nhg.game.networking.message.outgoing.ServerPacket;
import com.nhg.game.room.Room;
import com.nhg.game.room.entity.Action;
import com.nhg.game.utils.Int2;
import com.nhg.game.utils.Int3;
import com.nhg.game.utils.Rotation;
import com.nhg.game.utils.pathfinder.AStar;
import com.nhg.game.utils.pathfinder.Tile;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

@Slf4j
public class MovementComponent extends Component {

    @Getter
    @Setter
    private Int2 destination;

    @Getter
    @Setter
    private Set<Action> actions;

    private final AStar aStar;
    private Queue<Tile> calculatedPath;

    public MovementComponent() {
        this.calculatedPath = new LinkedList<>();
        this.aStar = new AStar(true);

        this.destination = new Int2(0, 0);

        this.actions = new HashSet<>();
    }

    public PositionComponent getPositionComponent() throws ComponentNotFoundException {
        PositionComponent positionComponent = (PositionComponent) entity.getComponent(ComponentType.Position);

        if (positionComponent == null) {
            log.error("PositionComponent not found on entity "+ entity.getId() +" with MovementComponent");
            throw new ComponentNotFoundException();
        }

        return positionComponent;
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
            Int3 position = getPositionComponent().getPosition();

            calculatedPath = aStar.findPath(
                    entity.getRoom().getRoomLayout().getTile(position.getX(), position.getY()),
                    entity.getRoom().getRoomLayout().getTile(destination.getX(), destination.getY()),
                    entity.getRoom().getRoomLayout());

            log.debug("Calculate Path from current " + position + " to destination " + destination);
        } catch (ComponentNotFoundException e) {
            log.error("PositionComponent in required when using MovementComponent: " + e);
        }
    }

    /**
     * Add the given action to the entity.
     * If the action is <code>MOVE</code> it will remove all the actions that should be removed on move.
     *
     * @param action the action to add.
     * @see Action#shouldBeRemovedOnMove
     */
    private void addAction(Action action) {
        actions.add(action);

        if (action == Action.MOVE) {
            actions.removeIf(Action::shouldBeRemovedOnMove);
        }
    }

    private void removeAction(Action action) {
        this.actions.remove(action);
    }

    /**
     * Process the entity movement. It's executed every entity cycle.
     *
     * @see #cycle
     * @see #calculatedPath
     */
    @Override
    public void cycle() {
        if (calculatedPath == null || calculatedPath.isEmpty()) {
            if (actions.contains(Action.MOVE)) {
                removeAction(Action.MOVE);
                sendUpdate(entity.getRoom());
            }

            return;
        }

        try {
            Int3 nextPosition = calculatedPath.poll().getPosition();
            addAction(Action.MOVE);

            BodyHeadRotationComponent bhrComponent =
                    (BodyHeadRotationComponent) entity.getComponent(ComponentType.BodyHeadRotation);

            if (bhrComponent != null) {
                bhrComponent.setRotation(
                        Rotation.CalculateRotation(getPositionComponent().getPosition().ToInt2XY(), nextPosition.ToInt2XY()));
            }



            getPositionComponent().setPosition(nextPosition);

            sendUpdate(entity.getRoom());
        } catch (ComponentNotFoundException e) {
            log.error("PositionComponent in required when using MovementComponent: " + e);
        }
    }

    private void sendUpdate(Room room) {
        room.getUsers().sendBroadcastMessage(new ServerPacket(OutgoingPacketHeaders.UpdateEntity, entity));
    }

    @Override
    public JSONObject toJson() throws JSONException {
        return null;
    }
}