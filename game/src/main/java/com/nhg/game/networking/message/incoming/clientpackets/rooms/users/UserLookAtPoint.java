package com.nhg.game.networking.message.incoming.clientpackets.rooms.users;

import com.nhg.game.networking.WebSocketClient;
import com.nhg.game.networking.message.incoming.ClientPacket;
import com.nhg.game.networking.message.outgoing.OutgoingPacketHeaders;
import com.nhg.game.networking.message.outgoing.ServerPacket;
import com.nhg.game.room.Room;
import com.nhg.game.room.RoomService;
import com.nhg.game.user.User;
import com.nhg.game.utils.BeanRetriever;
import com.nhg.game.utils.Int2;
import com.nhg.game.utils.Rotation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserLookAtPoint extends ClientPacket {

    private final RoomService roomService;

    public UserLookAtPoint() {
        roomService = BeanRetriever.get(RoomService.class);
    }

    @Override
    public void handle() {
        try {
            WebSocketClient wsClient = (WebSocketClient) client;
            User user = wsClient.getUser();

            if (user == null || user.getEntity() == null) return;

            int roomId = body.getInt("roomId");
            int x = body.getInt("x");
            int y = body.getInt("y");

            if(x == user.getEntity().getPosition().getX() && y == user.getEntity().getPosition().getY()) {
                return;
            }

            Rotation rotation = Rotation.CalculateRotation(new Int2(user.getEntity().getPosition().getX(), user.getEntity().getPosition().getY()), new Int2(x, y));
            user.getEntity().setBodyRotation(rotation);
            user.getEntity().setHeadRotation(rotation);

            Room room = roomService.getRoomById(roomId);

            room.getUsers().sendBroadcastMessage(
                    new ServerPacket(OutgoingPacketHeaders.UpdateEntity, user.getEntity().toJson()));

        } catch(Exception e) {
            log.error("Error: "+ e);
            e.printStackTrace();
        }
    }
}
