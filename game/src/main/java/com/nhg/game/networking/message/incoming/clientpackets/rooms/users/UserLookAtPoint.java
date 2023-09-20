package com.nhg.game.networking.message.incoming.clientpackets.rooms.users;

import com.nhg.game.networking.WebSocketClient;
import com.nhg.game.networking.message.incoming.ClientPacket;
import com.nhg.game.networking.message.outgoing.OutgoingPacketHeaders;
import com.nhg.game.networking.message.outgoing.ServerPacket;
import com.nhg.game.room.Room;
import com.nhg.game.room.RoomService;
import com.nhg.game.room.entity.Entity;
import com.nhg.game.room.entity.component.BodyHeadRotationComponent;
import com.nhg.game.room.entity.component.ComponentType;
import com.nhg.game.room.entity.component.PositionComponent;
import com.nhg.game.user.User;
import com.nhg.game.utils.BeanRetriever;
import com.nhg.game.utils.Position2;
import com.nhg.game.utils.Rotation;

public class UserLookAtPoint extends ClientPacket {

    private final RoomService roomService;

    public UserLookAtPoint() {
        roomService = BeanRetriever.get(RoomService.class);
    }

    @Override
    public void handle() throws Exception {
        WebSocketClient wsClient = (WebSocketClient) client;
        User user = wsClient.getUser();

        if (user == null || user.getEntity() == null) return;

        int roomId = body.getInt("roomId");
        int x = body.getInt("x");
        int y = body.getInt("y");

        Entity entity = user.getEntity();

        if (entity == null) return;

        PositionComponent pc = (PositionComponent) entity.getComponent(ComponentType.Position);
        BodyHeadRotationComponent bhr = (BodyHeadRotationComponent) entity.getComponent(ComponentType.BodyHeadRotation);

        if(x == pc.getPosition().getX() && y == pc.getPosition().getY()) {
            return;
        }

        Rotation rotation = Rotation.CalculateRotation(
                new Position2(pc.getPosition().getX(), pc.getPosition().getY()), new Position2(x, y));

        bhr.setRotation(rotation);

        Room room = roomService.getActiveRoomById(roomId);

        room.getUsers().sendBroadcastMessage(
                new ServerPacket(OutgoingPacketHeaders.UpdateEntity, user.getEntity().toJson()));
    }
}
