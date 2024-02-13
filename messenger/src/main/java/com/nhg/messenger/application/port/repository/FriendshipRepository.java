package com.nhg.messenger.application.port.repository;

import com.nhg.messenger.domain.Friendship;

import java.util.Optional;

public interface FriendshipRepository {

    void save(Friendship friendship);

    Optional<Friendship> findByIds(int fromId, int toId);
}
