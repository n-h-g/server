package com.nhg.game.networking.message.incoming.clientpackets.rooms.users;

import com.nhg.game.networking.WebSocketClient;
import com.nhg.game.networking.message.incoming.ClientPacket;
import com.nhg.game.networking.message.outgoing.OutgoingPacketHeaders;
import com.nhg.game.networking.message.outgoing.ServerPacket;
import com.nhg.game.room.Room;
import com.nhg.game.room.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;

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
