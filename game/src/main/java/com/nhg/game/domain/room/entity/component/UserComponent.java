package com.nhg.game.domain.room.entity.component;

import com.nhg.game.domain.user.User;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class UserComponent extends Component {

    private final int userId;

    public UserComponent(@NonNull User user) {
        this.userId = user.getId();
    }

    @Override
    public void cycle() {

    }
}