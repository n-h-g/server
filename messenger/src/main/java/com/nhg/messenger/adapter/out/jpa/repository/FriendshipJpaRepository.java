package com.nhg.messenger.adapter.out.jpa.repository;

import com.nhg.messenger.adapter.out.jpa.entity.FriendshipJpa;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FriendshipJpaRepository extends CrudRepository<FriendshipJpa, Integer> {

    @Query(value = "SELECT * FROM friendship WHERE user1 = :id1 AND user2 = :id2 OR user1 = :id2 AND user2 = :id1 LIMIT 1",
            nativeQuery = true)
    Optional<FriendshipJpa> getFriendshipByIds(@Param("id1") int id1, @Param("id2") int id2);
}
