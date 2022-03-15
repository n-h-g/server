package com.cubs3d.game.room.entity.component;

import com.cubs3d.game.user.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerInputComponent extends Component {

    private User user;

    public PlayerInputComponent(ComponentType type) {
        super(type);
    }
}
