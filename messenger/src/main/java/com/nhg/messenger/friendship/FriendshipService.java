package com.nhg.messenger.friendship;

import com.nhg.messenger.dto.FriendResponse;
import com.nhg.messenger.dto.FriendshipRequest;
import com.nhg.messenger.dto.FriendshipResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;
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
     * Add a friendship
     *
     * @param friendshipRequest the request
     * @return the friends list
     */
    public FriendResponse addFriend(FriendshipRequest friendshipRequest) {
        if (!existsUserWithId(friendshipRequest.senderId()) || !existsUserWithId(friendshipRequest.senderId())) {
            throw new IllegalArgumentException("Invalid ids");
        }

        boolean pending = true;

        Friendship friendship = this.getFriendshipBySenderOrDestination(friendshipRequest.senderId(), friendshipRequest.destinationId());

        if(friendship != null) {
            pending = false;
        } else {
            friendship = Friendship.builder()
                    .senderId(friendshipRequest.senderId())
                    .destinationId(friendshipRequest.destinationId()).build();
        }

        friendship.setPending(pending);
        friendshipRepository.save(friendship);

        return new FriendResponse(friendship.getId().intValue(), friendship.getSenderId(), friendship.getDestinationId(), friendship.isPending());
    }

    /**
     * Remove friendship
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
     * Confirm a friendship
     *
     * @param friendshipRequest the request
     * @return the friends list
     */
    public FriendshipResponse acceptFriendship(FriendshipRequest friendshipRequest) {

        Friendship friendship = this.friendshipRepository.findBySenderIdAndDestinationId(friendshipRequest.senderId(), friendshipRequest.destinationId());

        if (friendship == null) throw new IllegalArgumentException("Invalid friendship");

        // accept the friend request
        friendship.setPending(false);

        friendshipRepository.save(friendship);

        return new FriendshipResponse(null);
    }

    /**
     * Get 1:1 relationship between two friends
     * @param senderId
     * @param destinationId
     * @return friendship or null if the friendship does not exist.
     */
    public Friendship getFriendshipBySenderOrDestination(@NonNull Integer senderId, @NonNull Integer destinationId) {
        Friendship friendship = this.friendshipRepository.findBySenderIdAndDestinationId(senderId, destinationId);

        if(friendship == null) {
            friendship = this.friendshipRepository.findBySenderIdAndDestinationId(destinationId, senderId);
        }

        return friendship;
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
