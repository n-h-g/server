package com.nhg.game.networking.message.incoming.clientpackets.rooms.users;

import com.nhg.game.networking.WebSocketClient;
import com.nhg.game.networking.message.incoming.ClientPacket;
import com.nhg.game.room.RoomService;
import com.nhg.game.room.entity.Entity;
import com.nhg.game.room.entity.component.ComponentType;
import com.nhg.game.room.entity.component.MovementComponent;
import com.nhg.game.user.User;
import com.nhg.game.utils.BeanRetriever;
import com.nhg.game.utils.Position2;

public class UserMove extends ClientPacket {

    private final RoomService roomService;

    public UserMove() {
        roomService = BeanRetriever.get(RoomService.class);
    }

    @Override
    public void handle() throws Exception {
        WebSocketClient wsClient = (WebSocketClient) client;
        User user = wsClient.getUser();

        if (user == null || user.getEntity() == null) return;

        int x = body.getInt("x");
        int y = body.getInt("y");

        Entity entity = user.getEntity();

        if (entity == null) return;

        MovementComponent mc = (MovementComponent) entity.getComponent(ComponentType.Movement);

        mc.setDestination(new Position2(x,y));
        mc.calculatePath();
    }
}
