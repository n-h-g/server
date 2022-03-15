package com.cubs3d.game.room.entity.component;

import lombok.Getter;

public abstract class Component {

    @Getter
    private ComponentType type;

    public Component(ComponentType type) {
        this.type = type;
    }
}
