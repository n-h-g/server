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
import org.json.JSONObject;

public class UserTypeStatus extends ClientPacket {
    private final RoomService roomService;

    public UserTypeStatus() {
        roomService = BeanRetriever.get(RoomService.class);
    }

    @Override
    public void handle() throws Exception {
        WebSocketClient wsClient = (WebSocketClient) client;
        User user = wsClient.getUser();

        Entity entity = user.getEntity();

        if (entity == null) return;

        boolean typing = body.getBoolean("typing");

        Room room = entity.getRoom();

        room.getUsers().sendBroadcastMessage(new ServerPacket(OutgoingPacketHeaders.RoomUserType, new JSONObject()
                .put("id", entity.getId())
                .put("typing", typing)));
    }
}
