package com.cubs3d.game.room.entity.component;

import com.cubs3d.game.utils.Int3;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovementComponent extends Component {

    private Int3 position;

    public MovementComponent(ComponentType type) {
        super(type);
    }
}
