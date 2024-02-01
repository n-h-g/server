package com.nhg.game.adapter.out.persistence.jpa.repository;

import com.nhg.game.adapter.out.persistence.jpa.entity.RoomJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomJpaRepository extends JpaRepository<RoomJpa, Integer> {
}
