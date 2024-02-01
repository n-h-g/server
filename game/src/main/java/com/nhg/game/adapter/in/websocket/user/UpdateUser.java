package com.nhg.game.adapter.in.websocket.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhg.game.adapter.in.websocket.ClientUserMap;
import com.nhg.game.adapter.in.websocket.IncomingPacket;
import com.nhg.game.adapter.out.websocket.OutPacketHeaders;
import com.nhg.game.adapter.out.websocket.OutgoingPacket;
import com.nhg.game.application.dto.UserResponse;
import com.nhg.game.domain.user.User;
import com.nhg.game.infrastructure.context.BeanRetriever;

public class UpdateUser extends IncomingPacket {

    private final ClientUserMap clientUserMap;
    private final ObjectMapper objectMapper;

    public UpdateUser() {
        clientUserMap = BeanRetriever.get(ClientUserMap.class);
        objectMapper = BeanRetriever.get(ObjectMapper.class);
    }

    @Override
    public void handle() throws Exception {
        User user = clientUserMap.getUser(client.getId());

        if (user == null) return;

        client.sendMessage(new OutgoingPacket(
                OutPacketHeaders.UpdateUserInformation,
                objectMapper.writeValueAsString(UserResponse.fromDomain(user))
        ));
    }
}
