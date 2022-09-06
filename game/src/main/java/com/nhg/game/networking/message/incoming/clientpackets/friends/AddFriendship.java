package com.nhg.game.networking.message.incoming.clientpackets.friends;

import com.nhg.game.dto.FriendResponse;
import com.nhg.game.dto.FriendshipRequest;
import com.nhg.game.networking.WebSocketClient;
import com.nhg.game.networking.message.incoming.ClientPacket;
import com.nhg.game.networking.message.outgoing.OutgoingPacketHeaders;
import com.nhg.game.networking.message.outgoing.ServerPacket;
import com.nhg.game.user.User;
import com.nhg.game.user.UserService;
import com.nhg.game.utils.FriendAction;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class AddFriendship extends ClientPacket {

    private final RestTemplate restTemplate;
    private final UserService userService;

    public AddFriendship() {
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

            FriendResponse response = this.addFriend(new FriendshipRequest(
                    -1,
                    user.getId(),
                    id
            ));

            boolean isOnline = userService.hasUserOnline(id);

            if(!isOnline) return;

            // check if it's a friend request
            if(response.pending()) {
                userService.getActiveUser(id).getClient().sendMessage(new ServerPacket(OutgoingPacketHeaders.BubbleAlert,
                    new JSONObject()
                            .put("message", user.getUsername() + " ti ha inviato una richiesta di amicizia")
                            .put("goalId", user.getId())
                ));
            } else {
                // else accept the friend request
                ServerPacket packet1 = new ServerPacket(OutgoingPacketHeaders.UpdateFriendStatus,
                        new JSONObject()
                                .put("friend", destination.toJson())
                                .put("action", FriendAction.ADD_FRIEND)
                );
                ServerPacket packet2 = new ServerPacket(OutgoingPacketHeaders.UpdateFriendStatus,
                        new JSONObject()
                                .put("friend", user.toJson())
                                .put("action", FriendAction.ADD_FRIEND)
                );
                user.getClient().sendMessage(packet1);
                userService.getActiveUser(id).getClient().sendMessage(packet2);

            }

        }catch(Exception e) {
            log.error(e.getMessage());
        }
    }

    private FriendResponse addFriend(FriendshipRequest message) {
        return restTemplate.getForObject(
                "http://MESSENGER/api/v1/messenger/friendship/add/{senderId}/{destinationId}",
                FriendResponse.class,
                message.senderId(), message.destinationId()
        );
    }
}
