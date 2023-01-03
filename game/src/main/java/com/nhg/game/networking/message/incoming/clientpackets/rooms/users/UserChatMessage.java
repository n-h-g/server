package com.nhg.game.networking.message.incoming.clientpackets.rooms.users;

import com.nhg.game.dto.ChatMessageRequest;
import com.nhg.game.dto.ChatMessageResponse;
import com.nhg.game.networking.WebSocketClient;
import com.nhg.game.networking.message.incoming.ClientPacket;
import com.nhg.game.networking.message.outgoing.OutgoingPacketHeaders;
import com.nhg.game.networking.message.outgoing.ServerPacket;
import com.nhg.game.room.Room;
import com.nhg.game.user.User;
import com.nhg.game.utils.BeanRetriever;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class UserChatMessage extends ClientPacket {

    private final RestTemplate restTemplate;

    public UserChatMessage() {
        restTemplate = BeanRetriever.get("restTemplate", RestTemplate.class);
    }

    @Override
    public void handle() {
        try {
            WebSocketClient wsClient = (WebSocketClient) client;

            String message = body.getString("message");
            boolean isShout = body.getBoolean("shout");

            User user = wsClient.getUser();
            Room room = user.getEntity().getRoom();

            ChatMessageResponse response = this.sendMessage(new ChatMessageRequest(
                    user.getId(),
                    room.getId(),
                    message,
                    true
            ));

            room.getUsers().sendBroadcastMessage(new ServerPacket(
                    OutgoingPacketHeaders.RoomChatMessage,
                    new JSONObject()
                            .put("id", response.id())
                            .put("message", response.text())
                            .put("shout", isShout)
                            .put("authorId", user.getEntity().getId())
            ));

        } catch(Exception e) {
            log.error("Error: "+ e);
        }
    }

    private ChatMessageResponse sendMessage(ChatMessageRequest message) {
        return restTemplate.postForObject(
                "http://MESSENGER/api/v1/messenger/chat/send_message/",
                message,
                ChatMessageResponse.class
        );
    }
}
