package com.cubs3d.messenger.friendship;

import com.cubs3d.messenger.dto.ChatMessageRequest;
import com.cubs3d.messenger.dto.ChatMessageResponse;
import com.cubs3d.messenger.dto.FriendshipRequest;
import com.cubs3d.messenger.dto.FriendshipResponse;
import com.cubs3d.messenger.service.ChatMessageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

    @GetMapping("/accept/{id}")
    public FriendshipResponse acceptFriendShip(FriendshipRequest friendshipRequest) {
        return friendshipService.acceptFriendship(friendshipRequest.friendshipId());
    }

    @GetMapping("/add/{senderId}/{destinationId}")
    public FriendshipResponse addFriend(FriendshipRequest friendshipRequest) {
        return friendshipService.addFriend(friendshipRequest);
    }
}
