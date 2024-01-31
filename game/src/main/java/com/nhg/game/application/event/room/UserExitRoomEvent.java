package com.nhg.game.application.event.room;

import com.nhg.common.domain.event.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserExitRoomEvent implements DomainEvent {

    private int userId;
    private int roomId;

}