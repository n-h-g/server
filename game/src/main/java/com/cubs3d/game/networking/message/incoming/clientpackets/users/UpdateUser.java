package com.cubs3d.game.networking.message.incoming.clientpackets.users;

import com.cubs3d.game.networking.Clients;
import com.cubs3d.game.networking.WebSocketClient;
import com.cubs3d.game.networking.WebSocketHandler;
import com.cubs3d.game.networking.message.incoming.ClientPacket;
import com.cubs3d.game.networking.message.outgoing.OutgoingPacketHeaders;
import com.cubs3d.game.networking.message.outgoing.ServerPacket;
import com.cubs3d.game.room.RoomService;
import com.cubs3d.game.user.User;
import com.cubs3d.game.user.UserService;
import com.cubs3d.game.utils.Int2;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;

@Slf4j
public class UpdateUser extends ClientPacket {

    private final UserService userService;
    private final Clients clients;

    public UpdateUser() {
        userService = this.getBean(UserService.class);
        clients = this.getBean(Clients.class);
    }

    @Override
    public void handle() {
        try {
            WebSocketClient wsClient = (WebSocketClient) client;
            User user = wsClient.getUser();


            clients.SendBroadcastMessage(new ServerPacket(
                    OutgoingPacketHeaders.UpdateUserInformation,
                    user
            ));

        } catch(Exception e) {
            log.error("Error: "+ e);
            e.printStackTrace();
        }
    }
}
