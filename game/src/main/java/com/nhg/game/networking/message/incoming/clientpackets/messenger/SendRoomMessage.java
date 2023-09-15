package com.nhg.game.networking.message.incoming.clientpackets.messenger;

import com.nhg.game.dto.ChatMessageRequest;
import com.nhg.game.dto.ChatMessageResponse;
import com.nhg.game.networking.WebSocketClient;
import com.nhg.game.networking.message.incoming.ClientPacket;
import com.nhg.game.networking.message.outgoing.OutgoingPacketHeaders;
import com.nhg.game.networking.message.outgoing.ServerPacket;
import com.nhg.game.room.Room;
import com.nhg.game.user.User;
import com.nhg.game.utils.BeanRetriever;
import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

public class SendRoomMessage extends ClientPacket {

    private final RestTemplate restTemplate;

    public SendRoomMessage() {
        restTemplate = BeanRetriever.get("restTemplate", RestTemplate.class);
    }

    @Override
    public void handle() throws Exception {
        WebSocketClient wsClient = (WebSocketClient) client;
        User user = wsClient.getUser();

        if (user == null || user.getEntity() == null) return;

        String text = body.getString("text");

        Room room = user.getEntity().getRoom();

        ChatMessageRequest request = new ChatMessageRequest(
                user.getId(),
                room.getId(),
                text,
                true);

        ChatMessageResponse response = restTemplate.postForObject(
                "http://MESSENGER/api/v1/messenger/chat",
                request,
                ChatMessageResponse.class
        );

        if (response == null) return;

        room.getUsers().sendBroadcastMessage(new ServerPacket(OutgoingPacketHeaders.RoomChatMessage, new JSONObject()
                .put("text", response.text())
                .put("authorId", user.getEntity().getId())
                )
        );
    }
}
