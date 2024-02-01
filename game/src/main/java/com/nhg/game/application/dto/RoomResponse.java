package com.nhg.game.application.dto;

import com.nhg.game.application.repository.ActiveRoomRepository;
import com.nhg.game.domain.room.Room;
import com.nhg.game.infrastructure.context.BeanRetriever;

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

        ActiveRoomRepository activeRoomRepository = BeanRetriever.get(ActiveRoomRepository.class);

        int usersCount = room.getUsers().isEmpty()
                ? activeRoomRepository.findById(room.getId())
                        .map(activeRoom -> activeRoom.getUsers().size())
                        .orElse(0)
                : room.getUsers().size();

        return new RoomResponse(
                room.getId(),
                room.getName(),
                room.getDescription(),
                room.getOwner().getId(),
                room.getOwner().getUsername(),
                room.getRoomLayout().getDoorX(),
                room.getRoomLayout().getDoorY(),
                room.getRoomLayout().getDoorRotation().getValue(),
                usersCount
        );
    }
}