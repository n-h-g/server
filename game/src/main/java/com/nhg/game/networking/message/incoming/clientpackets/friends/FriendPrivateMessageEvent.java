package com.nhg.game.networking.message.incoming.clientpackets.friends;

import com.nhg.game.dto.ChatMessageRequest;
import com.nhg.game.dto.ChatMessageResponse;
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
public class FriendPrivateMessageEvent extends ClientPacket {

    private final RestTemplate restTemplate;
    private final UserService userService;

    public FriendPrivateMessageEvent() {
        restTemplate = this.getBean("restTemplate", RestTemplate.class);
        userService = this.getBean(UserService.class);
    }

    @Override
    public void handle() {
        try {
            WebSocketClient wsClient = (WebSocketClient) client;

            User user = wsClient.getUser();

            int destinationId = body.getInt("destinationId");
            String message = body.getString("message");

            User destination = userService.getUserById(destinationId);

            ChatMessageResponse response = this.sendMessage(
                    new ChatMessageRequest(
                            user.getId(), destination.getId(), message, false
                    )
            );

            if (response.id() == -1) return;

            if (!destination.isOnline()) return;

            destination.getClient().sendMessage(new ServerPacket(OutgoingPacketHeaders.FriendPrivateMessage,
                    new JSONObject()
                            .put("id", response.id())
                            .put("senderId", user.getId())
                            .put("destinationId", destination.getId())
                            .put("friendshipId", destination.getId())
                            .put("text", response.text())
            ));

        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private ChatMessageResponse sendMessage(ChatMessageRequest message) {
        return restTemplate.getForObject(
                "http://MESSENGER/api/v1/messenger/chat/send_message/{senderId}/{destinationId}/{text}/{isRoomMessage}",
                ChatMessageResponse.class,
                message.senderId(), message.destinationId(), message.text(), message.isRoomMessage()
        );
    }
}
