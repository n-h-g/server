package com.nhg.game.networking.message.incoming.clientpackets.navigator;

import com.nhg.game.networking.message.incoming.ClientPacket;
import com.nhg.game.networking.message.outgoing.OutgoingPacketHeaders;
import com.nhg.game.networking.message.outgoing.ServerPacket;
import com.nhg.game.room.Room;
import com.nhg.game.room.RoomService;
import lombok.extern.slf4j.Slf4j;
import java.util.List;

@Slf4j
public class GetAllRooms extends ClientPacket {

    private final RoomService roomService;

    public GetAllRooms() {
        roomService = this.getBean(RoomService.class);
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
