package com.nhg.game.domain.room.entity.component;

import com.nhg.game.domain.item.interaction.Interaction;
import com.nhg.game.domain.room.entity.Entity;

public class InteractionComponent extends Component implements Interaction {
    private final Interaction interaction;

    public InteractionComponent(Interaction interaction) {
        this.interaction = interaction;
    }

    @Override
    public void cycle() {
        interaction.onCycle(entity);
    }

    public void onClick(Entity interactingEntity) {
        interaction.onClick(entity, interactingEntity);
    }

    public void onMove(Entity interactingEntity) {
        interaction.onMove(entity, interactingEntity);
    }

    public void onRotate(Entity interactingEntity) {
        interaction.onRotate(entity, interactingEntity);
    }

    public void onWalkOn(Entity interactingEntity) {
        interaction.onWalkOn(entity, interactingEntity);
    }

    public void onWalkOff(Entity interactingEntity) {
        interaction.onWalkOff(entity, interactingEntity);
    }

    public void onStandIn(Entity interactingEntity) {
        interaction.onStandIn(entity, interactingEntity);
    }

    public void onPlace(Entity interactingEntity) {
        interaction.onPlace(entity, interactingEntity);
    }

    public void onPickUp(Entity interactingEntity) {
        interaction.onPickUp(entity, interactingEntity);
    }
}
