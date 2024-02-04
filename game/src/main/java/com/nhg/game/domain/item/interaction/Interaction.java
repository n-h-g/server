package com.nhg.game.domain.item.interaction;

import com.nhg.game.domain.item.RoomItem;
import com.nhg.game.domain.room.entity.Entity;

public interface Interaction {
    default void onCycle(Entity entity) {}
    default void onClick(Entity entity, Entity interactingEntity) {}
    default void onMove(Entity entity, Entity interactingEntity) {}
    default void onRotate(Entity entity, Entity interactingEntity) {}
    default void onWalkOn(Entity entity, Entity interactingEntity) {}
    default void onWalkOff(Entity entity, Entity interactingEntity) {}
    default void onStandIn(Entity entity, Entity interactingEntity) {}
    default void onPlace(Entity entity, Entity interactingEntity) {}
    default void onPickUp(Entity entity, Entity interactingEntity) {}
    default String getExtraData(Entity entity) { return ""; }


    static Interaction fromItem(RoomItem item) {
        return switch (item.getPrototype().getInteraction()) {
            case "multi_state" -> new MultiStateInteraction(item.getExtraData(), item.getPrototype().getStateCount());
            default -> new NoInteraction();
        };
    }
}