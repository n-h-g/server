package com.nhg.messenger.adapter.out.jpa;

import com.nhg.messenger.adapter.out.jpa.entity.FriendshipJpa;
import com.nhg.messenger.adapter.out.jpa.repository.FriendshipJpaRepository;
import com.nhg.messenger.application.port.repository.FriendshipRepository;
import com.nhg.messenger.domain.Friendship;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FriendshipJpaRepositoryAdapter implements FriendshipRepository {

    private final FriendshipJpaRepository friendshipJpaRepository;


    @Override
    public void save(Friendship friendship) {
        friendshipJpaRepository.save(FriendshipJpa.fromDomain(friendship));
    }

    @Override
    public Optional<Friendship> findByIds(int fromId, int toId) {
        return friendshipJpaRepository.getFriendshipByIds(fromId, toId)
                .map(FriendshipJpa::toFriendship);
    }
}
