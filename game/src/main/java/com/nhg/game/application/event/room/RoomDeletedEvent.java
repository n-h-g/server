package com.nhg.game.application.event.room;

import com.nhg.common.domain.event.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoomDeletedEvent implements DomainEvent {

    private int roomId;

}
