package com.nhg.game.application.event.room;

import com.nhg.common.domain.event.DomainEvent;
import com.nhg.game.domain.room.entity.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateRoomEntityEvent implements DomainEvent {

    private Entity entity;

}
