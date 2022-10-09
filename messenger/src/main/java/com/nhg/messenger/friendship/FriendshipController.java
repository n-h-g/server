package com.nhg.messenger.friendship;

import com.nhg.messenger.dto.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("api/v1/messenger/friendship")
public class FriendshipController {
    private final FriendshipService friendshipService;

    @GetMapping("/get_friends/{senderId}")
    public FriendshipResponse getFriends(FriendshipRequest friendshipRequest) {
        return friendshipService.getFriendsById(friendshipRequest.senderId());
    }

    @GetMapping("/accept/{senderId}/{destinationId}")
    public FriendshipResponse acceptFriendShip(FriendshipRequest friendshipRequest) {
        return friendshipService.acceptFriendship(friendshipRequest);
    }

    @GetMapping("/add/{senderId}/{destinationId}")
    public FriendResponse addFriend(FriendshipRequest friendshipRequest) {
        return friendshipService.addFriend(friendshipRequest);
    }

    @GetMapping("/delete/{senderId}/{destinationId}")
    public FriendshipResponse removeFriendship(FriendshipRequest friendshipRequest) {
        return friendshipService.removeFriendship(friendshipRequest);
    }
}
