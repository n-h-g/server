package com.nhg.game.domain.room.entity.component;

import com.nhg.game.domain.shared.position.Position3;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PositionComponent extends Component {

    private Position3 position;

    public PositionComponent() {
        position = Position3.Zero;
    }

    public PositionComponent(Position3 position) {
        this.position = position;
    }

    @Override
    public void cycle() {

    }

}
