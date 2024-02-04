package com.nhg.game.domain.item.interaction;

import com.nhg.game.application.event.room.UpdateRoomEntityEvent;
import com.nhg.game.domain.room.entity.Entity;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MultiStateInteraction implements Interaction {

    private Short extraData;
    private final short stateCount;

    public MultiStateInteraction(String extraData, short stateCount) {
        this.extraData = Short.parseShort(extraData);
        this.stateCount = stateCount;
    }

    @Override
    public void onClick(Entity entity, Entity interactingEntity) {
        try {
            if (stateCount <= 0) return;

            short currentState = extraData;

            if ((currentState + 1) % stateCount == currentState) return;

            extraData = (short) ((currentState + 1) % stateCount);

            entity.getEventPublisher().publish(new UpdateRoomEntityEvent(entity));

        } catch (Exception e) {
            log.error("Incorrect extraData for item with entityId: "+ entity.getId());
        }
    }

    @Override
    public String getExtraData() {
        return extraData.toString();
    }
}