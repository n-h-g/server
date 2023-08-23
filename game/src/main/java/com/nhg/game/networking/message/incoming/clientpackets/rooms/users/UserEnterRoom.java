package com.nhg.game.networking.message.incoming.clientpackets.rooms.users;

import com.nhg.game.networking.WebSocketClient;
import com.nhg.game.networking.message.incoming.ClientPacket;
import com.nhg.game.networking.message.outgoing.OutgoingPacketHeaders;
import com.nhg.game.networking.message.outgoing.ServerPacket;
import com.nhg.game.room.Room;
import com.nhg.game.room.RoomService;
import com.nhg.game.user.User;
import com.nhg.game.utils.BeanRetriever;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserEnterRoom extends ClientPacket {

    private final RoomService roomService;

    public UserEnterRoom() {
        roomService = BeanRetriever.get(RoomService.class);
    }

    @Override
    public void handle() throws Exception {
        WebSocketClient wsClient = (WebSocketClient) client;
        User user = wsClient.getUser();

        if (user == null) return;

        int roomId = body.getInt("id");

        if (user.getEntity() != null) {
            user.getEntity().getRoom().getUsers().sendBroadcastMessage(
                    new ServerPacket(OutgoingPacketHeaders.RemoveRoomEntity, user.getEntity().getId().toString()));

            roomService.userExitRoom(user);
        }

        if(!roomService.userEnterRoom(user, roomId)) {
            log.debug("User "+ user.getId() + " entered room with id " + roomId + " but it doesn't exist.");
            return;
        }

        Room room = roomService.getRoomById(roomId);

        client.sendMessage(new ServerPacket(OutgoingPacketHeaders.SendRoomData, room));
        client.sendMessage(new ServerPacket(OutgoingPacketHeaders.LoadRoomEntities, room.getEntities().values()));

        room.getUsers().sendBroadcastMessage(
                new ServerPacket(OutgoingPacketHeaders.AddRoomEntity, user.getEntity()));
    }
}
