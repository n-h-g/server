package com.cubs3d.game.networking.message.incoming.clientpackets.rooms.users;

import com.cubs3d.game.networking.WebSocketClient;
import com.cubs3d.game.networking.message.incoming.ClientPacket;
import com.cubs3d.game.networking.message.outgoing.serverpackets.rooms.SendRoomData;
import com.cubs3d.game.networking.message.outgoing.serverpackets.rooms.users.LoadUsersInRoom;
import com.cubs3d.game.networking.message.outgoing.serverpackets.rooms.users.NewRoomUser;
import com.cubs3d.game.networking.message.outgoing.serverpackets.rooms.users.RemoveRoomUser;
import com.cubs3d.game.room.Room;
import com.cubs3d.game.room.RoomService;
import com.cubs3d.game.user.User;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserExitRoom extends ClientPacket {

    private final RoomService roomService;

    public UserExitRoom() {
        roomService = this.getBean(RoomService.class);
    }

    @Override
    public void handle() {
        try {
            WebSocketClient wsClient = (WebSocketClient) client;
            User user = wsClient.getUser();

            if (user == null) return;

            int roomId = body.getInt("id");

            if(!roomService.userExitRoom(user, roomId)) {
                log.debug("User "+ user.getId() + " exited room with id " + roomId + " but it doesn't exist.");
                return;
            }

            Room room = roomService.getRoomById(roomId);

            room.getUsers().SendBroadcastMessage(new RemoveRoomUser(user));

        } catch(Exception e) {
            log.error("Error: "+ e);
        }
    }
}
