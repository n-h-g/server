package com.nhg.messenger.application.usecase;

import com.nhg.common.domain.UseCase;
import com.nhg.messenger.application.port.repository.FriendshipRepository;
import com.nhg.messenger.domain.Friendship;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@UseCase
@RequiredArgsConstructor
public class SendFriendshipUseCase {

    private final FriendshipRepository friendshipRepository;


    /**
     * Sends a friendship request.
     *
     * @param fromId the user's id that sends the friendship request.
     * @param toId the user's id that receives the friendship request.
     * @return true if a friendship is made, false if the friend already exists or
     *         if a pending friend request is created.
     */
    public boolean sendFriendship(int fromId, int toId) {
        Optional<Friendship> friendshipOpt = friendshipRepository.findByIds(fromId, toId);

        // users are not friend and there is not a friendship request pending
        if (friendshipOpt.isEmpty()) {
            friendshipRepository.save(new Friendship(fromId, toId, true));
            return false;
        }

        Friendship friendship = friendshipOpt.get();

        // users are already friends
        if (!friendship.isPending() || friendship.getId().getUser1() == fromId) return false;

        // there was a pending friendship request
        friendship.setPending(false);
        friendshipRepository.save(friendship);

        return true;

    }

}