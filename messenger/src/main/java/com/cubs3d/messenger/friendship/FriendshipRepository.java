package com.cubs3d.messenger.friendship;

import org.springframework.beans.factory.annotation.Autowired;
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
}
