package com.nhg.game.domain.room.entity.component;

import com.nhg.game.domain.shared.position.Rotation;

public class RotationComponent extends Component {

    private Rotation rotation;
    private final boolean useDiagonalRotations;

    public RotationComponent(Rotation rotation, boolean useDiagonalRotations) {
        this.rotation = rotation;
        this.useDiagonalRotations = useDiagonalRotations;
    }

    public RotationComponent(Rotation rotation) {
        this.rotation = rotation;
        this.useDiagonalRotations = false;
    }

    public Rotation rotate() {
        rotation = Rotation.intToRotation(rotation.getValue() + (useDiagonalRotations ? 1 : 2));
        return rotation;
    }

    @Override
    public void cycle() {

    }
}
