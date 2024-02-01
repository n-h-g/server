package com.nhg.game.adapter.out.persistence.jpa.repository;

import com.nhg.game.adapter.out.persistence.jpa.entity.RoomJpa;
import com.nhg.game.domain.room.Room;
import com.nhg.game.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomJpaRepository extends JpaRepository<RoomJpa, Integer> {

    List<Room> findByOwner(User owner);
}
