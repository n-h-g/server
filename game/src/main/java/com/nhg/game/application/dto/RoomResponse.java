package com.nhg.game.application.dto;

import com.nhg.game.domain.room.Room;
import com.nhg.game.domain.user.User;

public record RoomResponse(
        String name,
        String description,
        String layout,
        User owner,
        int doorX,
        int doorY,
        int doorDirection,
        int maxUsers
) {

    public static RoomResponse fromDomain(Room room) {
        return new RoomResponse(
                room.getName(),
                room.getDescription(),
                room.getRoomLayout().getLayout(),
                room.getOwner(),
                room.getRoomLayout().getDoorPosition().getX(),
                room.getRoomLayout().getDoorPosition().getY(),
                room.getRoomLayout().getDoorRotation().getValue(),
                100
        );
    }
}
