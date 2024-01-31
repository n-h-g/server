package com.nhg.game.application.dto;

import com.nhg.game.domain.room.Room;

public record RoomResponse(
        int id,
        String name,
        String description,
        int ownerId,
        String ownerName,
        int doorX,
        int doorY,
        int doorRotation,
        int usersCount
) {

    public static RoomResponse fromDomain(Room room) {
        return new RoomResponse(
                room.getId(),
                room.getName(),
                room.getDescription(),
                room.getOwner().getId(),
                room.getOwner().getUsername(),
                room.getRoomLayout().getDoorX(),
                room.getRoomLayout().getDoorY(),
                room.getRoomLayout().getDoorRotation().getValue(),
                room.getUsers().size()
        );
    }
}