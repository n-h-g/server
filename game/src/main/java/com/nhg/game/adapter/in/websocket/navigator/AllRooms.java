package com.nhg.game.adapter.in.websocket.navigator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhg.game.adapter.in.websocket.IncomingPacket;
import com.nhg.game.adapter.out.websocket.OutPacketHeaders;
import com.nhg.game.adapter.out.websocket.OutgoingPacket;
import com.nhg.game.application.dto.RoomResponse;
import com.nhg.game.application.repository.ActiveRoomRepository;
import com.nhg.game.infrastructure.context.BeanRetriever;

import java.util.List;

public class AllRooms extends IncomingPacket {

    private final ActiveRoomRepository activeRoomRepository;
    private final ObjectMapper objectMapper;

    public AllRooms() {
        activeRoomRepository = BeanRetriever.get(ActiveRoomRepository.class);
        objectMapper = BeanRetriever.get(ObjectMapper.class);
    }

    @Override
    public void handle() throws Exception {
        List<RoomResponse> rooms = activeRoomRepository.getAll().stream()
                .map(RoomResponse::fromDomain).toList();

        client.sendMessage(new OutgoingPacket(
                OutPacketHeaders.SendAllRooms,
                objectMapper.writeValueAsString(rooms)
        ));
    }
}
