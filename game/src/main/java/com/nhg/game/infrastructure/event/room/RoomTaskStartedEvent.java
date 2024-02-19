package com.nhg.game.infrastructure.event.room;

import com.nhg.game.infrastructure.event.InfrastructureEvent;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoomTaskStartedEvent implements InfrastructureEvent {

    private int roomId;
}
