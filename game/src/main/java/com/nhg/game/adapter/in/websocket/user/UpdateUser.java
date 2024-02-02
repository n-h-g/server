package com.nhg.game.adapter.in.websocket.user;

import com.nhg.game.adapter.in.websocket.ClientUserMap;
import com.nhg.game.adapter.in.websocket.IncomingPacket;
import com.nhg.game.adapter.out.websocket.OutPacketHeaders;
import com.nhg.game.adapter.out.websocket.OutgoingPacket;
import com.nhg.game.domain.user.User;
import com.nhg.game.infrastructure.context.BeanRetriever;
import com.nhg.game.infrastructure.mapper.UserToJsonMapper;

public class UpdateUser extends IncomingPacket {

    private final ClientUserMap clientUserMap;
    private final UserToJsonMapper userMapper;

    public UpdateUser() {
        clientUserMap = BeanRetriever.get(ClientUserMap.class);
        userMapper = BeanRetriever.get(UserToJsonMapper.class);
    }

    @Override
    public void handle() throws Exception {
        User user = clientUserMap.getUser(client.getId());

        if (user == null) return;

        client.sendMessage(new OutgoingPacket(
                OutPacketHeaders.UpdateUserInformation,
                userMapper.userToJson(user)
        ));
    }
}
