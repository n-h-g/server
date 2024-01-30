package com.nhg.game.domain.room.layout;

import com.nhg.game.domain.shared.position.Position3;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Tile {

    public enum State { OPEN, CLOSE }

    @Setter
    private State state;

    private final Position3 position;

    public Tile(Position3 position) {
        this.position = position;
        this.state = position.getZ() == 0 ? State.CLOSE : State.OPEN;
    }
}
