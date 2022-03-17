package com.cubs3d.game.room.entity;

import com.cubs3d.game.room.Room;
import com.cubs3d.game.utils.AStar;
import com.cubs3d.game.utils.Int2;
import com.cubs3d.game.utils.Int3;
import com.cubs3d.game.utils.Rotation;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public abstract class RoomEntity {

    @Getter
    private final Integer id;

    @Getter
    @Setter
    private Int2 position;

    @Getter
    @Setter
    private Int2 nextPosition;

    @Getter
    @Setter
    private Int2 destination;

    @Getter
    @Setter
    private Rotation bodyRotation;

    @Getter
    @Setter
    private Rotation headRotation;

    private AStar aStar;
    private List<Int2> calculatedPath;

    @Getter
    private final Room room;

    protected RoomEntity(Integer id, Room room) {
        this.id = id;
        this.room = room;

        this.calculatedPath = new ArrayList<>();
        this.aStar = new AStar();

        this.position = new Int2(0,0);
        this.destination = new Int2(0,0);
    }

    public void cycle() {
        move();
    }

    protected void move() {
        if (calculatedPath.isEmpty()) return;

        nextPosition = calculatedPath.remove(0);

        try {
            room.getRoomLayout().getTile(nextPosition.getX(), nextPosition.getY());
        } catch (IndexOutOfBoundsException e) {
            aStar.addClosedTile(nextPosition);
            calculatePath();

            if (calculatedPath.isEmpty()) return;

            nextPosition = calculatedPath.remove(0);
        }

        bodyRotation = Rotation.CalculateRotation(position, nextPosition);

        position = nextPosition;
        onPositionSet();
    }

    protected abstract void onPositionSet();

    public void calculatePath() {
        calculatedPath = aStar.findPath(position, destination);
        log.debug("Calculate Path current position "+ position +", destination "+ destination);
    }

}
