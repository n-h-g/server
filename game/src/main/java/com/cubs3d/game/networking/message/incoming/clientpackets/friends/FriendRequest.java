package com.cubs3d.game.networking.message.incoming.clientpackets.friends;

import com.cubs3d.game.dto.FriendshipRequest;
import com.cubs3d.game.dto.FriendshipResponse;
import com.cubs3d.game.networking.WebSocketClient;
import com.cubs3d.game.networking.message.incoming.ClientPacket;
import com.cubs3d.game.networking.message.outgoing.OutgoingPacketHeaders;
import com.cubs3d.game.networking.message.outgoing.ServerPacket;
import com.cubs3d.game.user.User;
import com.cubs3d.game.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class FriendRequest extends ClientPacket {

    private final RestTemplate restTemplate;
    private final UserService userService;

    public FriendRequest() {
        restTemplate = this.getBean("restTemplate", RestTemplate.class);
        userService = this.getBean(UserService.class);
    }

    @Override
    public void handle() {
        try {
            WebSocketClient wsClient = (WebSocketClient) client;

            int id = body.getInt("id");

            User user = wsClient.getUser();

            User destinatary = userService.getUserById(id);

            FriendshipResponse response = this.addFriend(new FriendshipRequest(
                    user.getId(),
                    user.getId(),
                    destinatary.getId()
            ));

            destinatary.getClient().sendMessage(new ServerPacket(OutgoingPacketHeaders.BubbleAlert,
                    new JSONObject()
                            .put("data", user.getUsername() + "ti ha inviato una richiesta di amicizia")
            ));

        }catch(Exception e) {
            log.error(e.getMessage());
        }
    }

    private FriendshipResponse addFriend(FriendshipRequest message) {
        return restTemplate.getForObject(
                "http://MESSENGER/api/v1/messenger/friendship/add/{senderId}/{destinationId}",
                FriendshipResponse.class,
                message.friendshipId(), message.senderId(), message.destinationId()
        );
    }
}
