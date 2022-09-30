package com.nhg.game.networking.message.incoming.clientpackets.friends;

import com.nhg.game.dto.FriendResponse;
import com.nhg.game.dto.FriendshipRequest;
import com.nhg.game.networking.WebSocketClient;
import com.nhg.game.networking.message.incoming.ClientPacket;
import com.nhg.game.networking.message.outgoing.OutgoingPacketHeaders;
import com.nhg.game.networking.message.outgoing.ServerPacket;
import com.nhg.game.user.User;
import com.nhg.game.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class AcceptFriendRequestEvent extends ClientPacket {

    private final RestTemplate restTemplate;
    private final UserService userService;

    public AcceptFriendRequestEvent() {
        restTemplate = this.getBean("restTemplate", RestTemplate.class);
        userService = this.getBean(UserService.class);
    }

    @Override
    public void handle() {
        try {
            WebSocketClient wsClient = (WebSocketClient) client;

            int id = body.getInt("id");

            User user = wsClient.getUser();

            User destination = userService.getUserById(id);

            this.acceptFriend(new FriendshipRequest(
                            -1,
                            destination.getId(),
                            user.getId()
                    )
            );

            if (destination.isOnline()) {
                destination.getClient().sendMessage(new ServerPacket(OutgoingPacketHeaders.BubbleAlert,
                        new JSONObject()
                                .put("message", destination.getUsername() + " accept your friend request")
                                .put("goalId", destination.getId())
                ));
            }

        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private FriendResponse acceptFriend(FriendshipRequest message) {
        return restTemplate.getForObject(
                "http://MESSENGER/api/v1/messenger/friendship/accept/{senderId}/{destinationId}",
                FriendResponse.class,
                message.senderId(), message.destinationId()
        );
    }
}
