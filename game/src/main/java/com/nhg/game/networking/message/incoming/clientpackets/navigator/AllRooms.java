package com.nhg.game.networking.message.incoming.clientpackets.navigator;

import com.nhg.game.networking.message.incoming.ClientPacket;
import com.nhg.game.networking.message.outgoing.OutgoingPacketHeaders;
import com.nhg.game.networking.message.outgoing.ServerPacket;
import com.nhg.game.room.Room;
import com.nhg.game.room.RoomService;
import com.nhg.game.utils.BeanRetriever;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class AllRooms extends ClientPacket {

    private final RoomService roomService;

    public AllRooms() {
        roomService = BeanRetriever.get(RoomService.class);
    }

    @Override
    public void handle() {
        try {
            List<Room> rooms = roomService.getActiveRooms();
            client.sendMessage(new ServerPacket(OutgoingPacketHeaders.SendAllRooms, rooms));

        } catch (Exception e) {
            log.error("Error: "+ e);
        }
    }
}
