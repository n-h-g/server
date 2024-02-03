package com.nhg.game.domain.room.entity.component;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class NameComponent extends Component {

    private String name;

    public NameComponent(@NonNull String name) {
        this.name = name;
    }

}
