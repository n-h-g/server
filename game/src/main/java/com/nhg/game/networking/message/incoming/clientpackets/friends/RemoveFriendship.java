package com.nhg.game.networking.message.incoming.clientpackets.friends;

import com.nhg.game.dto.FriendshipRequest;
import com.nhg.game.dto.FriendshipResponse;
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


            FriendshipResponse response = this.removeFriendship(new FriendshipRequest(
                    -1,
                    user.getId(),
                    id
            ));


            User destination = userService.getUserById(id);

            /*ServerPacket packet = new ServerPacket(OutgoingPacketHeaders.UpdateFriendStatus,
                    new JSONObject()
                            .put("friend", destination.toJson())
                            .put("action", FriendAction.DELETE_FRIEND)
            );*/

            /*ServerPacket packet2 = new ServerPacket(OutgoingPacketHeaders.UpdateFriendStatus,
                    new JSONObject()
                            .put("friend", user.toJson()
                            .put("action", FriendAction.DELETE_FRIEND)
            ));*/

            wsClient.sendMessage(new ServerPacket(OutgoingPacketHeaders.UpdateFriendStatus,
                    new JSONObject()
                            .put("friend", destination.toJson())
                            .put("action", FriendAction.DELETE_FRIEND)
            ));

            if(!destination.isOnline()) return;

            destination.getClient().sendMessage(new ServerPacket(OutgoingPacketHeaders.UpdateFriendStatus,
                    new JSONObject()
                            .put("friend", user.toJson())
                            .put("action", FriendAction.DELETE_FRIEND)
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
