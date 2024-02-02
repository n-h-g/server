package com.nhg.game.application.repository;

import com.nhg.game.domain.room.Room;

import java.util.List;
import java.util.Optional;

public interface ActiveRoomRepository {

    void add(Room room);

    void remove(int roomId);

    Optional<Room> findById(int roomId);

    List<Room> getAll();
}
