package com.nhg.game.adapter.in.websocket.navigator;

import com.nhg.game.adapter.in.websocket.ClientUserMap;
import com.nhg.game.adapter.in.websocket.IncomingPacket;
import com.nhg.game.adapter.out.websocket.OutPacketHeaders;
import com.nhg.game.adapter.out.websocket.OutgoingPacket;
import com.nhg.game.application.repository.RoomRepository;
import com.nhg.game.domain.room.Room;
import com.nhg.game.domain.user.User;
import com.nhg.game.infrastructure.context.BeanRetriever;
import com.nhg.game.infrastructure.mapper.RoomToJsonMapper;

import java.util.List;

public class MyRooms extends IncomingPacket {

    private final ClientUserMap clientUserMap;
    private final RoomRepository roomRepository;
    private final RoomToJsonMapper roomToJsonMapper;

    public MyRooms() {
        clientUserMap = BeanRetriever.get(ClientUserMap.class);
        roomRepository = BeanRetriever.get(RoomRepository.class);
        roomToJsonMapper = BeanRetriever.get(RoomToJsonMapper.class);
    }

    @Override
    public void handle() throws Exception {
        User user = clientUserMap.getUser(client.getId());

        if (user == null) return;

        List<Room> rooms = roomRepository.getRoomsByOwner(user);

        client.sendMessage(new OutgoingPacket(
                OutPacketHeaders.SendMyRooms,
                roomToJsonMapper.roomsToNavigatorRoomsJson(rooms)
        ));
    }
}
