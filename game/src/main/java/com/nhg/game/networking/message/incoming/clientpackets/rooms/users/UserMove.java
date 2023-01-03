package com.nhg.game.networking.message.incoming.clientpackets.rooms.users;

import com.nhg.game.networking.WebSocketClient;
import com.nhg.game.networking.message.incoming.ClientPacket;
import com.nhg.game.room.RoomService;
import com.nhg.game.user.User;
import com.nhg.game.utils.BeanRetriever;
import com.nhg.game.utils.Int2;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserMove extends ClientPacket {

    private final RoomService roomService;

    public UserMove() {
        roomService = BeanRetriever.get(RoomService.class);
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
