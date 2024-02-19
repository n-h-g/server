package com.nhg.game.application.usecase.room.entity;

import com.nhg.common.domain.UseCase;
import com.nhg.common.domain.event.DomainEventPublisher;
import com.nhg.game.application.repository.ActiveRoomRepository;
import com.nhg.game.application.repository.ItemRepository;
import com.nhg.game.domain.room.Room;
import com.nhg.game.domain.room.entity.Entity;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class LoadRoomEntitiesUseCase {

    private final ActiveRoomRepository activeRoomRepository;
    private final ItemRepository itemRepository;
    private final DomainEventPublisher eventPublisher;

    public void loadRoomEntities(int roomId) {
        Room room = activeRoomRepository.findById(roomId).orElseThrow();

        itemRepository.getItemsByRoomId(roomId)
                .forEach((item) -> {
                    Entity itemEntity = Entity.fromItem(item, room, eventPublisher);
                    item.setEntity(itemEntity);
                    room.addEntity(itemEntity);
                });
    }
}
