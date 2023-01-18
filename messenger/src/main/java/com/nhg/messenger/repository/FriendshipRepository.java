package com.nhg.messenger.repository;

import com.nhg.messenger.model.Friendship;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendshipRepository extends CrudRepository<Friendship, Integer> {

    @Query(value = "SELECT * FROM friendship WHERE user1 = :id1 AND user2 = :id2 OR user1 = :id2 AND user2 = :id1 LIMIT 1",
            nativeQuery = true)
    Friendship getFriendshipsByIds(@Param("id1") int id1, @Param("id2") int id2);
}
