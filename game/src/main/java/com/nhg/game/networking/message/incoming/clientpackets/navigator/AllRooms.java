package com.nhg.game.networking.message.incoming.clientpackets.navigator;

import com.nhg.game.networking.message.incoming.ClientPacket;
import com.nhg.game.networking.message.outgoing.OutgoingPacketHeaders;
import com.nhg.game.networking.message.outgoing.ServerPacket;
import com.nhg.game.room.Room;
import com.nhg.game.room.RoomService;
import com.nhg.game.utils.BeanRetriever;

import java.util.List;

public class AllRooms extends ClientPacket {

    private final RoomService roomService;

    public AllRooms() {
        roomService = BeanRetriever.get(RoomService.class);
    }

    @Override
    public void handle() throws Exception {
        List<Room> rooms = roomService.getActiveRooms();
        client.sendMessage(new ServerPacket(OutgoingPacketHeaders.SendAllRooms, rooms));
    }
}
