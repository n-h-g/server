package com.nhg.game.domain.room.entity.component;

import lombok.Getter;

public enum ComponentType {
    Name("name", NameComponent.class),
    User("user", UserComponent.class),
    //Item("item", ItemComponent.class),
    Position("position", PositionComponent.class),
    Rotation("rotation", RotationComponent.class),
    BodyHeadRotation("bh_rot", BodyHeadRotationComponent.class),
    Action("action", ActionComponent.class),
    HumanAspect("aspect", HumanAspectComponent.class),
    Movement("movement", MovementComponent.class);
    //Behaviour("behaviour", BehaviourComponent.class),
    //Interaction("interaction", InteractionComponent.class);

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