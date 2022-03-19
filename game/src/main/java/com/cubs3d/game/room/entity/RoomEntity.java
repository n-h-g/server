package com.cubs3d.game.room.entity;

import com.cubs3d.game.room.Room;
import com.cubs3d.game.room.layout.RoomLayout;
import com.cubs3d.game.utils.Int2;
import com.cubs3d.game.utils.Int3;
import com.cubs3d.game.utils.Rotation;
import com.cubs3d.game.utils.pathfinder.AStar;
import com.cubs3d.game.utils.pathfinder.Tile;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.Queue;

@Slf4j
public abstract class RoomEntity {

    @Getter
    private final Integer id;

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

    private final AStar aStar;
    private Queue<Tile> calculatedPath;

    @Getter
    private final Room room;

    protected RoomEntity(@NonNull Integer id, @NonNull String name, @NonNull Room room) {
        this.id = id;
        this.name = name;
        this.room = room;

        this.calculatedPath = new LinkedList<>();
        this.aStar = new AStar(true);

        this.position = new Int3(0,0, 0);
        this.destination = new Int2(0,0);
    }

    public void cycle() {
        move();
    }

    protected void move() {
        if (calculatedPath == null || calculatedPath.isEmpty()) return;

        Int3 nextPosition = calculatedPath.poll().getPosition();

        bodyRotation = Rotation.CalculateRotation(position.ToInt2XY(), nextPosition.ToInt2XY());

        position = nextPosition;
        onPositionSet();
    }

    protected abstract void onPositionSet();

    public void calculatePath() {
        RoomLayout layout = room.getRoomLayout();

        calculatedPath = aStar.findPath(
                layout.getTile(position.getX(), position.getY()),
                layout.getTile(destination.getX(), destination.getY()),
                room.getRoomLayout());

        log.debug("Calculate Path current position "+ position +", destination "+ destination);
    }

    public JSONObject toJsonObject() throws JSONException {
        return new JSONObject()
                .put("id", id)
                .put("name", name)
                .put("x", position.getX())
                .put("y", position.getY())
                .put("z", position.getZ())
                .put("body_rot", bodyRotation)
                .put("head_rot", headRotation);
    }

}
