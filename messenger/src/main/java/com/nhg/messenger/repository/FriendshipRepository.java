package com.nhg.messenger.repository;

import com.nhg.messenger.model.Friendship;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface FriendshipRepository extends CrudRepository<Friendship, Integer> {


    Optional<Friendship> findById(Integer Id);

    List<Friendship> findBySenderIdOrDestinationId(Integer senderId, Integer destinationId);

    Friendship findBySenderIdAndDestinationId(Integer senderId, Integer destinationId);

    List<Friendship> findBySenderId(Integer senderId);

    @Transactional
    void deleteBySenderIdAndDestinationId(Integer senderId, Integer destinationId);

    @Transactional
    void deleteBySenderIdOrDestinationId(Integer senderId, Integer destinationId);
}
