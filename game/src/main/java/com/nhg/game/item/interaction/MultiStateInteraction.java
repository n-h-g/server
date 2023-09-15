package com.nhg.game.item.interaction;

import com.nhg.game.networking.message.outgoing.OutgoingPacketHeaders;
import com.nhg.game.networking.message.outgoing.ServerPacket;
import com.nhg.game.room.entity.Entity;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;

@Slf4j
public class MultiStateInteraction implements Interaction {

    private short extraData;
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

            entity.getRoom().getUsers().sendBroadcastMessage(
                    new ServerPacket(OutgoingPacketHeaders.UpdateEntity, entity));

        } catch (Exception e) {
            log.error("Incorrect extraData for item with entityId: "+ entity.getId());
        }
    }

    @Override
    public JSONObject getExtraData(Entity entity) throws JSONException {
        return new JSONObject()
                .put("extraData", extraData);
    }
}
