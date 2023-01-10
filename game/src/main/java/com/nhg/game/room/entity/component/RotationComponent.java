package com.nhg.game.room.entity.component;

import com.nhg.game.utils.Rotation;
import org.json.JSONException;
import org.json.JSONObject;

public class RotationComponent extends Component {

    private Rotation rotation;
    private boolean useDiagonalRotations;

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
    public JSONObject toJson() throws JSONException {
        return new JSONObject()
                .put("rot", rotation.getValue());
    }

    @Override
    public void cycle() {

    }
}
