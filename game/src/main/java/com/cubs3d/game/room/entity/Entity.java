package com.cubs3d.game.room.entity;

import com.cubs3d.game.networking.message.outgoing.JsonSerializable;
import com.cubs3d.game.networking.message.outgoing.OutgoingPacketHeaders;
import com.cubs3d.game.networking.message.outgoing.ServerPacket;
import com.cubs3d.game.room.Room;
import com.cubs3d.game.room.layout.RoomLayout;
import com.cubs3d.game.utils.Action;
import com.cubs3d.game.utils.Int2;
import com.cubs3d.game.utils.Int3;
import com.cubs3d.game.utils.Rotation;
import com.cubs3d.game.utils.pathfinder.AStar;
import com.cubs3d.game.utils.pathfinder.Tile;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

@Slf4j
public abstract class Entity implements JsonSerializable {

    @Getter
    private final Integer id;

    @Getter
    private final EntityType type;

    @Getter
    private final String name;

    @Getter
    @Setter
    private Int3 position;

    @Getter
    @Setter
    private Int2 destination;

    @Getter
    @Setter
    private Rotation bodyRotation;

    @Getter
    @Setter
    private Rotation headRotation;

    @Getter
    @Setter
    private Set<Action> actions;

    private final AStar aStar;
    private Queue<Tile> calculatedPath;

    @Getter
    private final Room room;

    protected Entity(@NonNull Integer id, EntityType type, @NonNull String name, @NonNull Room room) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.room = room;

        this.calculatedPath = new LinkedList<>();
        this.aStar = new AStar(true);

        this.position = new Int3(0,0, 0);
        this.destination = new Int2(0,0);

        this.actions = new HashSet<>();

    }

    public void cycle() {
        move();
    }

    protected void move() {
        if (calculatedPath == null || calculatedPath.isEmpty()) {
            if (actions.contains(Action.MOVE)) {
                removeAction(Action.MOVE);
                update();
            }

            return;
        }

        addAction(Action.MOVE);
        Int3 nextPosition = calculatedPath.poll().getPosition();

        bodyRotation = Rotation.CalculateRotation(position.ToInt2XY(), nextPosition.ToInt2XY());

        position = nextPosition;
        update();
    }

    private void addAction(Action action) {
        this.actions.add(action);

        if (action == Action.MOVE) {
            actions.removeIf(Action::shouldBeRemovedOnMove);
        }
    }

    private void removeAction(Action action) {
        this.actions.remove(action);
    }

    protected void update() {
        room.getUsers().sendBroadcastMessage(new ServerPacket(OutgoingPacketHeaders.UpdateEntity, this));
    }

    public void calculatePath() {
        RoomLayout layout = room.getRoomLayout();

        calculatedPath = aStar.findPath(
                layout.getTile(position.getX(), position.getY()),
                layout.getTile(destination.getX(), destination.getY()),
                room.getRoomLayout());

        log.debug("Calculate Path current position "+ position +", destination "+ destination);
    }

    public JSONObject toJson() throws JSONException {
        JSONArray actions = new JSONArray();
        this.actions.forEach(action -> actions.put(action.getValue()));

        return new JSONObject()
                .put("id", id)
                .put("type", type.getCode())
                .put("name", name)
                .put("x", position.getX())
                .put("y", position.getY())
                .put("z", position.getZ())
                .put("body_rot", bodyRotation)
                .put("head_rot", headRotation)
                .put("actions", actions);
    }

}