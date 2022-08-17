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
public class DeclineFriendRequest extends ClientPacket {

    private RestTemplate restTemplate;
    private UserService userService;

    public DeclineFriendRequest() {
        restTemplate = this.getBean(RestTemplate.class);
        userService = this.getBean(UserService.class);
    }

    @Override
    public void handle() {
        try {
            WebSocketClient wsClient = (WebSocketClient) client;

            int id = body.getInt("id");

            User user = wsClient.getUser();

            FriendshipResponse response = this.declineRequest(new FriendshipRequest(
                    -1,
                    user.getId(),
                    id
            ));


        } catch(Exception e) {
            log.error(e.getMessage());
        }
    }
    private FriendshipResponse declineRequest(FriendshipRequest message) {
        return restTemplate.getForObject(
                "http://MESSENGER/api/v1/messenger/friendship/delete/{senderId}/{destinationId}",
                FriendshipResponse.class,
                message.senderId(), message.destinationId()
        );
    }
}
