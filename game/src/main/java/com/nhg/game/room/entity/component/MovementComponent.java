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

import java.util.LinkedList;
import java.util.Queue;

@Slf4j
public class MovementComponent extends Component {

    @Getter
    @Setter
    private Int2 destination;

    private final AStar aStar;
    private Queue<Tile> calculatedPath;


    public MovementComponent() {
        this.calculatedPath = new LinkedList<>();
        this.aStar = new AStar(true);

        this.destination = new Int2(0, 0);
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
                    sendUpdate(entity.getRoom());
                }

                return;
            }

            Int3 nextPosition = calculatedPath.poll().getPosition();
            actionComponent.addAction(Action.MOVE);

            if (bhrComponent != null) {
                bhrComponent.setRotation(
                        Rotation.CalculateRotation(positionComponent.getPosition().ToInt2XY(), nextPosition.ToInt2XY()));
            }

            positionComponent.setPosition(nextPosition);

            sendUpdate(entity.getRoom());
        } catch (ComponentNotFoundException e) {
            log.error("There are require components for MovementComponent that are missing: " + e);
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