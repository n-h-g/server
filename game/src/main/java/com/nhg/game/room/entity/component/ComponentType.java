package com.nhg.game.room.entity.component;

import lombok.Getter;

public enum ComponentType {
    Name("name", NameComponent.class),
    User("user", UserComponent.class),
    Position("position", PositionComponent.class),
    Movement("movement", MovementComponent.class),
    BodyHeadRotation("bh_rot", BodyHeadRotationComponent.class);

    @Getter
    private final String name;

    private final Class<? extends Component> clazz;

    ComponentType(String name, Class<? extends Component> clazz) {
        this.name = name;
        this.clazz = clazz;
    }

    public Class<? extends Component> getComponentClass() {
        return clazz;
    }


}