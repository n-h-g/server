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
public class RemoveFriendship extends ClientPacket {

    private final RestTemplate restTemplate;

    public RemoveFriendship() {
        restTemplate = this.getBean("restTemplate", RestTemplate.class);
    }

    @Override
    public void handle() {
        try {
            WebSocketClient wsClient = (WebSocketClient) client;

            int id = body.getInt("id");

            User user = wsClient.getUser();

            FriendshipResponse response = this.removeFriendship(new FriendshipRequest(
                    -1,
                    user.getId(),
                    id
            ));


        } catch(Exception e) {
            log.error(e.getMessage());
        }
    }
    private FriendshipResponse removeFriendship(FriendshipRequest message) {
        return restTemplate.getForObject(
                "http://MESSENGER/api/v1/messenger/friendship/delete/{senderId}/{destinationId}",
                FriendshipResponse.class,
                message.senderId(), message.destinationId()
        );
    }
}
