package com.nhg.game.utils.pathfinder;


import com.nhg.game.utils.Int3;
import lombok.Getter;
import lombok.Setter;

public class Tile implements Comparable<Tile> {

    public enum State { OPEN, CLOSE, INVALID };

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


    @Getter
    @Setter
    private boolean allowStack;

    @Getter
    @Setter
    private double stackHeight;

    public Tile(Int3 position) {
        this.position = position;
        this.state = position.getZ() == 0 ? State.CLOSE : State.OPEN;
        this.allowStack = false;
        this.stackHeight = 0;
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

    public void setStackHeight(double stackHeight) {
        if(this.state == State.INVALID) {
            this.stackHeight = Double.MAX_VALUE;
            this.allowStack = false;
            return;
        }

        if (stackHeight >= 0 && stackHeight != Double.MAX_VALUE) {
            this.stackHeight = stackHeight;
            this.allowStack = true;
        } else {
            this.allowStack = false;
            this.stackHeight = this.position.getZ();
        }

    }
}
