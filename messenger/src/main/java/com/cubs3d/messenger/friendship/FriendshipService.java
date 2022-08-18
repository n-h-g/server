package com.cubs3d.messenger.friendship;

import com.cubs3d.messenger.dto.FriendResponse;
import com.cubs3d.messenger.dto.FriendshipRequest;
import com.cubs3d.messenger.dto.FriendshipResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FriendshipService {
    private final FriendshipRepository friendshipRepository;
    private final RestTemplate restTemplate;

    /**
     *  Add a friendship
     *
     * @param friendshipRequest the request
     * @return the friends list
     */
    public FriendResponse addFriend(FriendshipRequest friendshipRequest) {
        if (!existsUserWithId(friendshipRequest.senderId()) || !existsUserWithId(friendshipRequest.senderId())) {
            throw new IllegalArgumentException("Invalid ids");
        }

        boolean isPending = true;

        List<Friendship> friendshipKey = this.friendshipRepository.findBySenderIdOrDestinationId(friendshipRequest.senderId(), friendshipRequest.destinationId());

        if(!friendshipKey.isEmpty()) {
            isPending = false;
        }

        Friendship friendship = Friendship.builder()
                .senderId(friendshipRequest.senderId())
                .destinationId(friendshipRequest.destinationId())
                .pending(isPending)
                .build();

        friendshipRepository.save(friendship);

        return new FriendResponse(friendshipRequest.friendshipId(), friendshipRequest.senderId(), friendshipRequest.destinationId(), isPending);
    }

    /**
     *  Remove friendship
     *
     * @param friendshipRequest the request
     * @return FriendshipResponse
     */
    public FriendshipResponse removeFriendship(FriendshipRequest friendshipRequest) {
        if (!existsUserWithId(friendshipRequest.senderId()) || !existsUserWithId(friendshipRequest.senderId())) {
            throw new IllegalArgumentException("Invalid ids");
        }

        this.friendshipRepository.deleteBySenderIdOrDestinationId(friendshipRequest.senderId(), friendshipRequest.destinationId());

        return new FriendshipResponse(null);
    }

    /**
     *  Confirm a friendship
     *
     * @param friendshipId the friendship id
     * @return the friends list
     */
    public FriendshipResponse acceptFriendship(Integer friendshipId) {
        Optional<Friendship> friendships = friendshipRepository.findById(friendshipId);

        if(friendships.isEmpty()) throw new IllegalArgumentException("Invalid friendship");

        Friendship friendship = friendships.get();

        // accept the friend request
        friendship.setPending(false);

        friendshipRepository.save(friendship);

        return new FriendshipResponse(null);

    }

    /**
     * Get the friends list with the given id
     *
     * @param senderId user id
     * @return the friends list
     */
    public FriendshipResponse getFriendsById(Integer senderId) {
        List<Friendship> friendships = friendshipRepository.findBySenderIdOrDestinationId(senderId, senderId);
        return new FriendshipResponse(friendships);
    }

    /**
     * Check if the user with the given id exists.
     *
     * @param id user's id.
     * @return true if the user exits.
     */
    private Boolean existsUserWithId(int id) {
        return restTemplate.getForObject(
                "http://GAME/api/v1/game/user/exists_with_id/{id}",
                Boolean.class,
                id
        );
    }
}
