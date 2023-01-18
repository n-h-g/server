package com.nhg.game.networking.message.incoming.clientpackets.navigator;

import com.nhg.game.networking.WebSocketClient;
import com.nhg.game.networking.message.incoming.ClientPacket;
import com.nhg.game.networking.message.outgoing.OutgoingPacketHeaders;
import com.nhg.game.networking.message.outgoing.ServerPacket;
import com.nhg.game.room.Room;
import com.nhg.game.room.RoomService;
import com.nhg.game.user.User;
import com.nhg.game.utils.BeanRetriever;

import java.util.List;

public class MyRooms extends ClientPacket {

    private final RoomService roomService;

    public MyRooms() {
        roomService = BeanRetriever.get(RoomService.class);
    }

    @Override
    public void handle() throws Exception {
        WebSocketClient wsClient = (WebSocketClient) client;
        User user = wsClient.getUser();

        if (user == null) return;

        List<Room> rooms = roomService.getRoomsByOwner(user);
        client.sendMessage(new ServerPacket(OutgoingPacketHeaders.SendMyRooms, rooms));
    }
}
