package com.nhg.game.networking.message.incoming.clientpackets.friends;

import com.nhg.game.dto.*;
import com.nhg.game.networking.WebSocketClient;
import com.nhg.game.networking.message.incoming.ClientPacket;
import com.nhg.game.networking.message.outgoing.OutgoingPacketHeaders;
import com.nhg.game.networking.message.outgoing.ServerPacket;
import com.nhg.game.user.User;
import com.nhg.game.user.UserService;
import com.nhg.game.utils.BeanRetriever;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class FriendsList extends ClientPacket {

    private final RestTemplate restTemplate;
    private final UserService userService;

    public FriendsList() {
        restTemplate = BeanRetriever.get("restTemplate", RestTemplate.class);
        userService = BeanRetriever.get(UserService.class);
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

            List<FriendResponse> friends = new ArrayList<>();


            for (FriendResponse friendData : response.friendships()) {

                int id = friendData.destinationId() == user.getId() ? friendData.senderId() : friendData.destinationId();

                User friend = this.userService.getUserById(id);
                friends.add(new FriendResponse(friend, friendData.friendshipId(), friendData.senderId(), friendData.destinationId(), friendData.pending()));
            }

            wsClient.sendMessage(new ServerPacket(
                    OutgoingPacketHeaders.FriendsList,
                    friends
            ));
        } catch(Exception e) {
            log.error(e.getMessage());
        }
    }

    private FriendshipResponse getFriendsList(FriendshipRequest message) {
        return restTemplate.getForObject(
                "http://MESSENGER/api/v1/messenger/friendship/get_friends/{senderId}",
                FriendshipResponse.class,
                message.senderId()
        );
    }
}
