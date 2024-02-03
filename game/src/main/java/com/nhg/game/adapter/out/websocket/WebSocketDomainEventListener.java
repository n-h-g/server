package com.nhg.game.adapter.out.websocket;

import com.nhg.common.domain.event.DomainEvent;
import com.nhg.game.adapter.in.websocket.mapper.EntityToJsonMapper;
import com.nhg.game.application.event.room.RemovedRoomEntityEvent;
import com.nhg.game.application.event.room.UpdateRoomEntityEvent;
import com.nhg.game.domain.room.entity.Entity;
import com.nhg.game.infrastructure.helper.BroadcastHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketDomainEventListener {

    private final EntityToJsonMapper entityMapper;

    @EventListener
    public void handleEvent(DomainEvent domainEvent) {
        try {
            switch (domainEvent) {

                case UpdateRoomEntityEvent event -> {
                    Entity entity = event.getEntity();

                    BroadcastHelper.sendBroadcastMessage(entity.getRoom().getUsers().values(), new OutgoingPacket(
                            OutPacketHeaders.UpdateEntity,
                            entityMapper.entityToJson(entity)
                    ));
                }

                case RemovedRoomEntityEvent event -> {
                    Entity entity = event.getEntity();

                    BroadcastHelper.sendBroadcastMessage(entity.getRoom().getUsers().values(), new OutgoingPacket(
                            OutPacketHeaders.RemoveRoomEntity,
                            entity.getId().toString()
                    ));
                }

                default -> { }
            }
        } catch (Exception e) {
            log.error("WebsocketDomainEventListener: Error while handling event");
        }
    }
}
