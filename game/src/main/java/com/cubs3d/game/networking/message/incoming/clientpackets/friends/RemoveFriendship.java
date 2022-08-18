package com.cubs3d.game.networking.message.incoming.clientpackets.friends;

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
public class RemoveFriendship extends ClientPacket {

    private final RestTemplate restTemplate;
    private final UserService userService;

    public RemoveFriendship() {
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

            FriendshipResponse response = this.removeFriendship(new FriendshipRequest(
                    -1,
                    user.getId(),
                    id
            ));

            ServerPacket packet = new ServerPacket(OutgoingPacketHeaders.UpdateFriendStatus,
                    new JSONObject()
                            .put("friend", destination.toJson())
                            .put("action", FriendAction.DELETE_FRIEND)
            );

            user.getClient().sendMessage(packet);
            userService.getActiveUser(id).getClient().sendMessage(packet);


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
