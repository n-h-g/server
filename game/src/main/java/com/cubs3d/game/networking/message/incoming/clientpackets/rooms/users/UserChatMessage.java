package com.cubs3d.game.networking.message.incoming.clientpackets.rooms.users;

import com.cubs3d.game.dto.ChatMessageRequest;
import com.cubs3d.game.dto.ChatMessageResponse;
import com.cubs3d.game.networking.WebSocketClient;
import com.cubs3d.game.networking.message.incoming.ClientPacket;
import com.cubs3d.game.networking.message.outgoing.OutgoingPacketHeaders;
import com.cubs3d.game.networking.message.outgoing.ServerPacket;
import com.cubs3d.game.room.Room;
import com.cubs3d.game.user.User;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class UserChatMessage extends ClientPacket {

    private final RestTemplate restTemplate;

    public UserChatMessage() {
        restTemplate = this.getBean("restTemplate", RestTemplate.class);
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
                            .put("message", response.text())
                            .put("shout", isShout)
                            .put("id", user.getEntity().getId())
            ));

        } catch(Exception e) {
            log.error("Error: "+ e);
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
