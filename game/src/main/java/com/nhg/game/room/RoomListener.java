package com.nhg.game.room;

import com.nhg.game.utils.BeanRetriever;
import lombok.NonNull;

import jakarta.persistence.PostLoad;

public class RoomListener {

    @PostLoad
    private void postLoad(@NonNull Room room) {
        RoomService roomService = BeanRetriever.get(RoomService.class);

        Room activeRoom = roomService.getActiveRoomById(room.getId());

        if (activeRoom == null) return;

        room.setUsersCount(activeRoom.getUsersCount());
    }
}
