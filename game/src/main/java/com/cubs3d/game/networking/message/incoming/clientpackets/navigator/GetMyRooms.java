package com.cubs3d.game.networking.message.incoming.clientpackets.navigator;

import com.cubs3d.game.networking.WebSocketClient;
import com.cubs3d.game.networking.message.incoming.ClientPacket;
import com.cubs3d.game.networking.message.outgoing.OutgoingPacketHeaders;
import com.cubs3d.game.networking.message.outgoing.ServerPacket;
import com.cubs3d.game.room.Room;
import com.cubs3d.game.room.RoomService;
import com.cubs3d.game.user.User;
import lombok.extern.slf4j.Slf4j;
import java.util.List;

@Slf4j
public class GetMyRooms extends ClientPacket {

    private final RoomService roomService;

    public GetMyRooms() {
        roomService = this.getBean(RoomService.class);
    }

    @Override
    public void handle() {
        try {
            WebSocketClient wsClient = (WebSocketClient) client;
            User user = wsClient.getUser();

            if (user == null) return;

            List<Room> rooms = roomService.getRoomsByOwner(user);

            client.sendMessage(new ServerPacket(OutgoingPacketHeaders.SendMyRooms, rooms));

        } catch (Exception e) {
            log.error("Error: "+ e);
        }
    }
}
