package com.nhg.game.networking.message.incoming.clientpackets.rooms.users;

import com.nhg.game.networking.WebSocketClient;
import com.nhg.game.networking.message.incoming.ClientPacket;
import com.nhg.game.networking.message.outgoing.OutgoingPacketHeaders;
import com.nhg.game.networking.message.outgoing.ServerPacket;
import com.nhg.game.room.Room;
import com.nhg.game.room.RoomService;
import com.nhg.game.room.entity.Entity;
import com.nhg.game.user.User;
import com.nhg.game.utils.BeanRetriever;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserExitRoom extends ClientPacket {

    private final RoomService roomService;

    public UserExitRoom() {
        roomService = BeanRetriever.get(RoomService.class);
    }

    @Override
    public void handle() throws Exception {
        WebSocketClient wsClient = (WebSocketClient) client;
        User user = wsClient.getUser();

        if (user == null) return;

        int roomId = body.getInt("id");

        if(!roomService.userExitRoom(user, roomId)) {
            log.debug("User "+ user.getId() + " exited room with id " + roomId + " but it doesn't exist.");
            return;
        }

        Room room = roomService.getActiveRoomById(roomId);

        Entity entity = user.getEntity();

        if (entity == null) return;

        room.getUsers().sendBroadcastMessage(
                new ServerPacket(OutgoingPacketHeaders.RemoveRoomEntity, entity.getId().toString()));
    }
}
