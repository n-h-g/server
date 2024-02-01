package com.nhg.game.adapter.out.persistence.inmemory;

import com.nhg.game.application.repository.ActiveRoomRepository;
import com.nhg.game.domain.room.Room;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ActiveRoomRepositoryInMemoryAdapter implements ActiveRoomRepository {

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

    @Override
    public List<Room> getAll() {
        return new ArrayList<>(activeRooms.values());
    }
}
