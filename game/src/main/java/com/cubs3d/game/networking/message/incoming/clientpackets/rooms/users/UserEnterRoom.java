package com.cubs3d.game.networking.message.incoming.clientpackets.rooms.users;

import com.cubs3d.game.networking.WebSocketClient;
import com.cubs3d.game.networking.message.incoming.ClientPacket;
import com.cubs3d.game.networking.message.outgoing.serverpackets.rooms.SendRoomData;
import com.cubs3d.game.room.Room;
import com.cubs3d.game.room.RoomService;
import com.cubs3d.game.user.User;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserEnterRoom extends ClientPacket {

    private RoomService roomService;

    public UserEnterRoom() {
        roomService = this.getBean(RoomService.class);
    }

    @Override
    public void handle() {
        try {
            WebSocketClient wsClient = (WebSocketClient) client;
            User user = wsClient.getUser();

            if (user == null) return;

            int roomId = body.getInt("id");

            if(!roomService.userEnterRoom(user, roomId)) return;

            Room room = roomService.getRoomById(roomId);

            client.SendMessage(new SendRoomData());

        } catch(Exception e) {
            log.error("Error: "+ e);
        }
    }
}
