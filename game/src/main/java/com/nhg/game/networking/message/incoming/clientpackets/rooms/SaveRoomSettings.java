package com.nhg.game.networking.message.incoming.clientpackets.rooms;

import com.nhg.game.networking.WebSocketClient;
import com.nhg.game.networking.message.incoming.ClientPacket;
import com.nhg.game.networking.message.outgoing.OutgoingPacketHeaders;
import com.nhg.game.networking.message.outgoing.ServerPacket;
import com.nhg.game.room.Room;
import com.nhg.game.room.RoomService;
import com.nhg.game.user.User;
import com.nhg.game.utils.BeanRetriever;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;

import java.util.Objects;

@Slf4j
public class SaveRoomSettings extends ClientPacket {

    private final RoomService roomService;

    public SaveRoomSettings() {
        roomService = BeanRetriever.get(RoomService.class);
    }

    @Override
    public void handle() throws Exception {
        WebSocketClient wsClient = (WebSocketClient) client;
        User user = wsClient.getUser();

        int id = body.getInt("id");
        boolean deleteRoom = body.getBoolean("deleteRoom");
        String name = body.getString("name");

        Room room = roomService.getRoomById(id);

        if (user == null || room == null) return;

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
            user.setEntity(null);
            roomService.delete(room);
            return;
        }

        room.setName(name);
        roomService.save(room);
        room.getUsers().sendBroadcastMessage(new ServerPacket(OutgoingPacketHeaders.SendRoomData, room));
    }
}
