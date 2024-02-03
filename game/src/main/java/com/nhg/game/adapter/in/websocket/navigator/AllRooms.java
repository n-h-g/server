package com.nhg.game.adapter.in.websocket.navigator;

import com.nhg.game.adapter.in.websocket.IncomingPacket;
import com.nhg.game.adapter.out.websocket.OutPacketHeaders;
import com.nhg.game.adapter.out.websocket.OutgoingPacket;
import com.nhg.game.application.repository.ActiveRoomRepository;
import com.nhg.game.domain.room.Room;
import com.nhg.game.infrastructure.context.BeanRetriever;
import com.nhg.game.infrastructure.mapper.RoomToJsonMapper;

import java.util.List;

public class AllRooms extends IncomingPacket {

    private final ActiveRoomRepository activeRoomRepository;
    private final RoomToJsonMapper roomToJsonMapper;

    public AllRooms() {
        activeRoomRepository = BeanRetriever.get(ActiveRoomRepository.class);
        roomToJsonMapper = BeanRetriever.get(RoomToJsonMapper.class);
    }

    @Override
    public void handle() throws Exception {
        List<Room> rooms = activeRoomRepository.getAll();

        client.sendMessage(new OutgoingPacket(
                OutPacketHeaders.SendAllRooms,
                roomToJsonMapper.roomsToNavigatorRoomsJson(rooms)
        ));
    }
}
