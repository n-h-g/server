package com.nhg.messenger.service;

import com.nhg.messenger.model.Friendship;
import com.nhg.messenger.repository.FriendshipRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class FriendshipService {
    private final FriendshipRepository friendshipRepository;
    private final RestTemplate restTemplate;

    /**
     * Sends a friendship request.
     *
     * @param fromId the user's id that sends the friendship request.
     * @param toId the user's id that receives the friendship request.
     * @return true if a friendship is made, false if the friend already exists or
     *         if a pending friend request is created.
     */
    public boolean requestFriendship(int fromId, int toId) {
        Friendship friendship = friendshipRepository.getFriendshipsByIds(fromId, toId);

        // users are not friend and there is not a friendship request pending
        if (friendship == null) {
            friendshipRepository.save(new Friendship(fromId, toId, true));
            return false;
        }

        // users are already friends
        if (!friendship.isPending() || friendship.getId().getUser1() == fromId) return false;

        // there was a pending friendship request
        friendship.setPending(false);
        friendshipRepository.save(friendship);
        return true;
    }


}
