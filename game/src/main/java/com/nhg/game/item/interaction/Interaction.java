package com.nhg.game.item.interaction;

import com.nhg.game.item.Item;
import com.nhg.game.room.entity.Entity;
import org.json.JSONException;
import org.json.JSONObject;

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
    default JSONObject getExtraData(Entity entity) throws JSONException { return null; }


    static Interaction fromItem(Item item) {
        return switch (item.getItemSpecification().getInteraction()) {
            case "multi_state" -> new MultiStateInteraction(item.getExtraData(), item.getItemSpecification().getStateCount());
            default -> new NoInteraction();
        };
    }
}
