package com.nhg.game.adapter.in.websocket.packet.user;

import com.nhg.game.adapter.in.websocket.ClientUserMap;
import com.nhg.game.adapter.in.websocket.IncomingPacket;
import com.nhg.game.adapter.in.websocket.mapper.UserToJsonMapper;
import com.nhg.game.adapter.out.websocket.OutPacketHeader;
import com.nhg.game.adapter.out.websocket.OutgoingPacket;
import com.nhg.game.domain.user.User;
import com.nhg.game.infrastructure.networking.Client;
import com.nhg.game.infrastructure.networking.packet.ClientPacket;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;

@RequiredArgsConstructor
@IncomingPacket(header = 15)
public class UpdateUser implements ClientPacket<JSONObject> {

    private final ClientUserMap clientUserMap;
    private final UserToJsonMapper userMapper;

    @Override
    public void handle(Client<?> client, JSONObject body) {
        User user = clientUserMap.getUser(client.getId());

        if (user == null) return;

        OutgoingPacket.send(
                client,
                OutPacketHeader.UpdateUserInformation,
                userMapper.userToJson(user)
        );
    }
}
