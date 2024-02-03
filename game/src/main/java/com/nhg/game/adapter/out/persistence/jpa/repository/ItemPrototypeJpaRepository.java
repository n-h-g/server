package com.nhg.game.adapter.out.persistence.jpa.repository;

import com.nhg.game.adapter.out.persistence.jpa.item.ItemPrototypeJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemPrototypeJpaRepository extends JpaRepository<ItemPrototypeJpa, Integer> {

    Optional<ItemPrototypeJpa> findByName(String name);

}
