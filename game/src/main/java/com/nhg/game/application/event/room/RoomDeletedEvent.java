package com.nhg.game.application.event.room;

import com.nhg.common.domain.event.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDeletedEvent implements DomainEvent {

    private int roomId;

}