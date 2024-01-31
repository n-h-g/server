package com.nhg.game.adapter.out.map;

import com.nhg.game.application.repository.ActiveRoomRepository;
import com.nhg.game.domain.room.Room;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ActiveRoomMapAdapter implements ActiveRoomRepository {

    private final Map<Integer, Room> activeRooms = new ConcurrentHashMap<>();

    @Override
    public void add(Room room) {
        activeRooms.put(room.getId(), room);
    }

    @Override
    public void remove(int roomId) {
        activeRooms.remove(roomId);
    }

    @Override
    public Optional<Room> findById(int roomId) {
        return Optional.ofNullable(activeRooms.get(roomId));
    }
}
