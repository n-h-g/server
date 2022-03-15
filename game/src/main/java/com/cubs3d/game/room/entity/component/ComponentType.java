package com.cubs3d.game.room.entity.component;

import lombok.Getter;
import lombok.experimental.Accessors;

public enum ComponentType {
    PLAYER_INPUT(PlayerInputComponent.class, "PlayerInput", "pi", false),
    MOVEMENT(MovementComponent.class, "Movement", "mv", false);


    @Getter
    private final Class<? extends Component> componentClass;

    @Getter
    private final String fullName;

    @Getter
    private final String abbreviatedName;

    @Getter @Accessors(fluent = true)
    private final boolean hasMultipleInstancesPerEntity;

    ComponentType(Class<? extends Component> componentClass, final String fullName, String abbreviatedName,
                  boolean hasMultipleInstancesPerEntity) {
        this.componentClass = componentClass;
        this.fullName = fullName;
        this.abbreviatedName = abbreviatedName;
        this.hasMultipleInstancesPerEntity = hasMultipleInstancesPerEntity;
    }
}
