package com.cubs3d.game.networking.message.incoming.clientpackets.friends;

import com.cubs3d.game.dto.FriendResponse;
import com.cubs3d.game.dto.FriendshipRequest;
import com.cubs3d.game.dto.FriendshipResponse;
import com.cubs3d.game.networking.WebSocketClient;
import com.cubs3d.game.networking.message.incoming.ClientPacket;
import com.cubs3d.game.networking.message.outgoing.OutgoingPacketHeaders;
import com.cubs3d.game.networking.message.outgoing.ServerPacket;
import com.cubs3d.game.user.User;
import com.cubs3d.game.user.UserService;
import com.cubs3d.game.utils.FriendAction;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.Server;
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
                            .put("goalId", destination.getId())
                ));
            } else {
                // else accept the friend request
                ServerPacket packet = new ServerPacket(OutgoingPacketHeaders.UpdateFriendStatus,
                        new JSONObject()
                                .put("friend", destination.toJson())
                                .put("action", FriendAction.ADD_FRIEND)
                );
                user.getClient().sendMessage(packet);
                userService.getActiveUser(id).getClient().sendMessage(packet);

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
