package com.cubs3d.game.networking.message.incoming.clientpackets.friends;

import com.cubs3d.game.dto.*;
import com.cubs3d.game.networking.WebSocketClient;
import com.cubs3d.game.networking.message.incoming.ClientPacket;
import com.cubs3d.game.networking.message.outgoing.JsonSerializable;
import com.cubs3d.game.networking.message.outgoing.OutgoingPacketHeaders;
import com.cubs3d.game.networking.message.outgoing.ServerPacket;
import com.cubs3d.game.user.User;
import com.cubs3d.game.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class FriendsList extends ClientPacket {

    private final RestTemplate restTemplate;
    private final UserService userService;

    public FriendsList() {
        restTemplate = this.getBean("restTemplate", RestTemplate.class);
        userService = this.getBean(UserService.class);
    }

    @Override
    public void handle() {
        try {
            WebSocketClient wsClient = (WebSocketClient) client;

            User user = wsClient.getUser();

            FriendshipResponse response = this.getFriendsList(new FriendshipRequest(
                    -1,
                                user.getId(),
                    -1
            ));

            List<User> friends = new ArrayList<>();

            System.out.println();

            for (FriendResponse friendData : response.friendships()) {
                User friend = this.userService.getUserById(friendData.senderId());
                friends.add(friend);
            }

            wsClient.sendMessage(new ServerPacket(
                    OutgoingPacketHeaders.FriendsList,
                    new JSONObject().put("friends", friends)
            ));
        } catch(Exception e) {
            log.error(e.getMessage());
        }
    }

    private FriendshipResponse getFriendsList(FriendshipRequest message) {
        return restTemplate.getForObject(
                "http://MESSENGER/api/v1/messenger/friendship/get_friends/{senderId}",
                FriendshipResponse.class,
                message.friendshipId(), message.senderId(), message.destinationId()
        );
    }
}
