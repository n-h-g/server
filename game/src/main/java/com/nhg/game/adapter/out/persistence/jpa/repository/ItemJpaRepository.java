package com.nhg.game.adapter.out.persistence.jpa.repository;

import com.nhg.game.adapter.out.persistence.jpa.item.ItemJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemJpaRepository extends JpaRepository<ItemJpa, Integer> {

    @Query(value = "SELECT * FROM items WHERE owner_id = :ownerId AND room_id IS NULL", nativeQuery = true)
    List<ItemJpa> inventoryItemsByOwnerId(@Param("ownerId") int ownerId);

}
