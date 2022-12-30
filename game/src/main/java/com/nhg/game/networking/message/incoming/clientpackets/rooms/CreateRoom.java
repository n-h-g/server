package com.nhg.game.networking.message.incoming.clientpackets.rooms;

import com.nhg.game.networking.WebSocketClient;
import com.nhg.game.networking.message.incoming.ClientPacket;
import com.nhg.game.networking.message.outgoing.OutgoingPacketHeaders;
import com.nhg.game.networking.message.outgoing.ServerPacket;
import com.nhg.game.room.Room;
import com.nhg.game.room.RoomService;
import com.nhg.game.user.User;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CreateRoom extends ClientPacket {

    private final RoomService roomService;

    public CreateRoom() {
        roomService = this.getBean(RoomService.class);
    }

    @Override
    public void handle() {
        try {
            WebSocketClient wsClient = (WebSocketClient) client;
            User user = wsClient.getUser();

            if (user == null) return;

            String name = body.getString("name");
            String desc = body.getString("desc");
            String layout = body.getString("layout");

            int door_x = body.getInt("door_x");
            int door_y = body.getInt("door_y");
            int door_dir = body.getInt("door_dir");
            int maxUsers = body.getInt("maxUsers");

            Room room = new Room(name, user, layout, door_x, door_y, door_dir);

            roomService.createRoom(room);
            client.sendMessage(new ServerPacket(OutgoingPacketHeaders.SendRoomData, room));


        }catch (Exception e) {
            log.debug(e.getMessage());
        }
    }
}