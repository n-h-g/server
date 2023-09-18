package com.nhg.game.utils.pathfinder;

import com.nhg.game.utils.Position3;
import lombok.Getter;
import lombok.Setter;

public class Tile implements Comparable<Tile> {

    public enum State { OPEN, CLOSE }

    @Getter
    @Setter
    private State state;

    @Getter
    @Setter
    private double gCost;

    @Getter
    @Setter
    private double hCost;

    @Getter
    private final Position3 position;

    @Getter
    @Setter
    private Tile previousTile;

    public Tile(Position3 position) {
        this.position = position;
        this.state = position.getZ() == 0 ? State.CLOSE : State.OPEN;
    }

    public double getFCost() {
        return gCost + hCost;
    }

    @Override
    public int compareTo(Tile o) {
        double dif = getFCost() - o.getFCost();
        return dif == 0 ? 0 : dif > 0 ? 1 : -1;
    }
}
