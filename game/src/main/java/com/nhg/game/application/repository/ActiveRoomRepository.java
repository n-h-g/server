package com.nhg.game.application.repository;

import com.nhg.game.domain.room.Room;

public interface ActiveRoomRepository {

    void add(Room room);

    void remove(int roomId);

    Room get(int roomId);
}
