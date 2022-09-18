package com.nhg.game.utils.pathfinder;


import com.nhg.game.utils.Int3;
import lombok.Getter;
import lombok.Setter;

public class Tile implements Comparable<Tile> {

    public enum State { OPEN, CLOSE, BLOCKED };

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
    private final Int3 position;

    @Getter
    @Setter
    private Tile previousTile;

    public Tile(Int3 position) {
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

    public boolean isWalkable() {
        return this.state == State.OPEN;
    }
}
