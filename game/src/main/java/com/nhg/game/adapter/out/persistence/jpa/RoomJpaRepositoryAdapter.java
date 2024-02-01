package com.nhg.game.adapter.out.persistence.jpa;

import com.nhg.game.adapter.out.persistence.jpa.entity.RoomJpa;
import com.nhg.game.adapter.out.persistence.jpa.repository.RoomJpaRepository;
import com.nhg.game.application.repository.RoomRepository;
import com.nhg.game.domain.room.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RoomJpaRepositoryAdapter implements RoomRepository {

    private final RoomJpaRepository roomJpaRepository;


    @Override
    public Room save(Room room) {
        RoomJpa roomJpa = roomJpaRepository.save(RoomJpa.fromDomain(room));

        return roomJpa.toRoom();
    }

    @Override
    public Optional<Room> findById(int roomId) {
        return roomJpaRepository.findById(roomId)
                .map(RoomJpa::toRoom);
    }

    @Override
    public void delete(int id) {
        Optional<RoomJpa> roomJpa = roomJpaRepository.findById(id);

        if (roomJpa.isEmpty()) return;

        roomJpaRepository.delete(roomJpa.get());
    }
}
