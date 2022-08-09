package com.cubs3d.game.networking.message.incoming.clientpackets.rooms.users;

import com.cubs3d.game.networking.WebSocketClient;
import com.cubs3d.game.networking.message.incoming.ClientPacket;
import com.cubs3d.game.networking.message.outgoing.OutgoingPacketHeaders;
import com.cubs3d.game.networking.message.outgoing.ServerPacket;
import com.cubs3d.game.room.Room;
import com.cubs3d.game.room.RoomService;
import com.cubs3d.game.user.User;
import com.cubs3d.game.utils.Int2;
import com.cubs3d.game.utils.Rotation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserLookAtPoint extends ClientPacket {

    private final RoomService roomService;

    public UserLookAtPoint() {
        roomService = this.getBean(RoomService.class);
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
