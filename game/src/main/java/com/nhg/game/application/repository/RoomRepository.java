package com.nhg.game.application.repository;

import com.nhg.game.domain.room.Room;
import com.nhg.game.domain.user.User;

import java.util.List;
import java.util.Optional;

public interface RoomRepository {

    Room save(Room room);

    Optional<Room> findById(int roomId);

    void delete(int id);

    List<Room> getRoomsByOwner(User owner);
}
