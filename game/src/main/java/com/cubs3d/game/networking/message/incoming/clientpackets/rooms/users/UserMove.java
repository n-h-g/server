package com.cubs3d.game.networking.message.incoming.clientpackets.rooms.users;

import com.cubs3d.game.networking.WebSocketClient;
import com.cubs3d.game.networking.message.incoming.ClientPacket;
import com.cubs3d.game.room.RoomService;
import com.cubs3d.game.user.User;
import com.cubs3d.game.utils.Int2;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserMove extends ClientPacket {

    private final RoomService roomService;

    public UserMove() {
        roomService = this.getBean(RoomService.class);
    }

    @Override
    public void handle() {
        try {
            WebSocketClient wsClient = (WebSocketClient) client;
            User user = wsClient.getUser();

            if (user == null || user.getEntity() == null) return;

            int x = body.getInt("x");
            int y = body.getInt("y");

            user.getEntity().setDestination(new Int2(x,y));
            user.getEntity().calculatePath();


        } catch(Exception e) {
            log.error("Error: "+ e);
            e.printStackTrace();
        }
    }
}
