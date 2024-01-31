package com.nhg.game.application.repository;

import com.nhg.game.domain.room.Room;

import java.util.Optional;

public interface RoomRepository {

    Room save(Room room);

    Optional<Room> find(int roomId);

    void delete(int id);
}
