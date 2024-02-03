package com.nhg.game.domain.room.entity.component;


import com.nhg.game.domain.shared.position.Rotation;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BodyHeadRotationComponent extends Component {


    private Rotation bodyRotation;
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

}
