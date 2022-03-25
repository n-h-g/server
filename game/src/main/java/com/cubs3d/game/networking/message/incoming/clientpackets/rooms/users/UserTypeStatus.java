package com.cubs3d.game.networking.message.incoming.clientpackets.rooms.users;

import com.cubs3d.game.dto.ChatMessageRequest;
import com.cubs3d.game.dto.ChatMessageResponse;
import com.cubs3d.game.networking.WebSocketClient;
import com.cubs3d.game.networking.message.incoming.ClientPacket;
import com.cubs3d.game.networking.message.outgoing.OutgoingPacketHeaders;
import com.cubs3d.game.networking.message.outgoing.ServerPacket;
import com.cubs3d.game.room.Room;
import com.cubs3d.game.room.RoomService;
import com.cubs3d.game.user.User;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class UserTypeStatus extends ClientPacket {
    private final RoomService roomService;

    public UserTypeStatus() {
        roomService = this.getBean(RoomService.class);
    }

    @Override
    public void handle() {
        try {
            WebSocketClient wsClient = (WebSocketClient) client;

            int roomId = body.getInt("roomId");
            boolean typing = body.getBoolean("typing");

            Room room = roomService.getRoomById(roomId);

            room.getUsers().sendBroadcastMessage(new ServerPacket(OutgoingPacketHeaders.RoomUserType, new JSONObject()
                    .put("id", wsClient.getUser().getId())
                    .put("typing", typing)));
        } catch(Exception e) {
            log.error("Error: "+ e);
        }
    }
}
