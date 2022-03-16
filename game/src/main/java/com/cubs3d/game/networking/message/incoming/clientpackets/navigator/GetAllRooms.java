package com.cubs3d.game.networking.message.incoming.clientpackets.navigator;

import com.cubs3d.game.networking.message.incoming.ClientPacket;
import com.cubs3d.game.networking.message.outgoing.serverpackets.navigator.SendAllRooms;
import com.cubs3d.game.room.Room;
import com.cubs3d.game.room.RoomService;
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

            client.SendMessage(new SendAllRooms(rooms));

        } catch (Exception e) {
            log.error("Error: "+ e);
        }
    }
}
