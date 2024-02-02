package com.nhg.game.adapter.in.websocket.navigator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhg.game.adapter.in.websocket.ClientUserMap;
import com.nhg.game.adapter.in.websocket.IncomingPacket;
import com.nhg.game.adapter.out.websocket.OutPacketHeaders;
import com.nhg.game.adapter.out.websocket.OutgoingPacket;
import com.nhg.game.application.dto.RoomResponse;
import com.nhg.game.application.repository.RoomRepository;
import com.nhg.game.domain.user.User;
import com.nhg.game.infrastructure.context.BeanRetriever;

import java.util.List;

public class MyRooms extends IncomingPacket {

    private final ClientUserMap clientUserMap;
    private final RoomRepository roomRepository;
    private final ObjectMapper objectMapper;

    public MyRooms() {
        clientUserMap = BeanRetriever.get(ClientUserMap.class);
        roomRepository = BeanRetriever.get(RoomRepository.class);
        objectMapper = BeanRetriever.get(ObjectMapper.class);
    }

    @Override
    public void handle() throws Exception {
        User user = clientUserMap.getUser(client.getId());

        if (user == null) return;

        List<RoomResponse> rooms = roomRepository.getRoomsByOwner(user).stream()
                .map(RoomResponse::fromDomain).toList();

        client.sendMessage(new OutgoingPacket(
                OutPacketHeaders.SendAllRooms,
                objectMapper.writeValueAsString(rooms)
        ));
    }
}
