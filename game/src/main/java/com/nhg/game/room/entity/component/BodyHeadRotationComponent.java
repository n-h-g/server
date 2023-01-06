package com.nhg.game.room.entity.component;

import com.nhg.game.utils.Rotation;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONException;
import org.json.JSONObject;

public class BodyHeadRotationComponent extends Component {

    @Getter
    @Setter
    private Rotation bodyRotation;

    @Getter
    @Setter
    private Rotation headRotation;

    public BodyHeadRotationComponent() {
        setRotation(Rotation.SOUTH);
    }

    public BodyHeadRotationComponent(Rotation rotation) {
        setRotation(rotation);
    }

    /**
     * Sets body and head rotation to the given rotation.
     *
     * @param rotation the rotation to set.
     */
    public void setRotation(Rotation rotation) {
        bodyRotation = rotation;
        headRotation = rotation;
    }

    @Override
    public JSONObject toJson() throws JSONException {
        return new JSONObject()
                .put("body_rot", bodyRotation.getValue())
                .put("head_rot", bodyRotation.getValue());
    }

    @Override
    public void cycle() {

    }
}
