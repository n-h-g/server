package com.nhg.game.adapter.out.persistence.jpa.repository;

import com.nhg.game.adapter.out.persistence.jpa.entity.UserJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserJpaRepository extends JpaRepository<UserJpa, Integer> {

    Optional<UserJpa> findByUsername(String username);
    Boolean existsById(int id);
}
