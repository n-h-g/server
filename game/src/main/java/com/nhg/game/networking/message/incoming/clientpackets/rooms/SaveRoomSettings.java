package com.nhg.game.networking.message.incoming.clientpackets.rooms;

import com.nhg.game.networking.WebSocketClient;
import com.nhg.game.networking.message.incoming.ClientPacket;
import com.nhg.game.networking.message.outgoing.OutgoingPacketHeaders;
import com.nhg.game.networking.message.outgoing.ServerPacket;
import com.nhg.game.room.Room;
import com.nhg.game.room.RoomService;
import com.nhg.game.user.User;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;

import java.util.Objects;

@Slf4j
public class SaveRoomSettings extends ClientPacket {

    private final RoomService roomService;

    public SaveRoomSettings() {
        roomService = this.getBean(RoomService.class);
    }

    @Override
    public void handle() {
        try{
            WebSocketClient wsClient = (WebSocketClient) client;
            User user = wsClient.getUser();

            int id = body.getInt("id");
            boolean deleteRoom = body.getBoolean("deleteRoom");
            String name = body.getString("name");

            Room room = roomService.getRoomById(id);

            // check if is owner
            if(!Objects.equals(room.getOwner().getId(), user.getId())) {
                log.debug("User is not allowed to edit this room");
                return;
            }

            if(deleteRoom) {
                room.getUsers().sendBroadcastMessage(new ServerPacket(OutgoingPacketHeaders.SendRoomData,
                        new JSONObject()
                                .put("id", -1))
                );
                roomService.deleteRoom(room);
                return;
            }

            room.setName(name);
            roomService.updateRoom(room);
            room.getUsers().sendBroadcastMessage(new ServerPacket(OutgoingPacketHeaders.SendRoomData, room));

        } catch(Exception e) {
            log.debug(e.getMessage());
        }
    }
}