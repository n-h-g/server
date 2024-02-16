package com.nhg.game.adapter.in.websocket.packet.navigator;

import com.nhg.game.adapter.in.InPacketHeader;
import com.nhg.game.adapter.in.websocket.ClientUserMap;
import com.nhg.game.adapter.in.websocket.IncomingPacket;
import com.nhg.game.adapter.in.websocket.mapper.RoomToJsonMapper;
import com.nhg.game.adapter.out.websocket.OutPacketHeader;
import com.nhg.game.adapter.out.websocket.OutgoingPacket;
import com.nhg.game.application.repository.RoomRepository;
import com.nhg.game.domain.room.Room;
import com.nhg.game.domain.user.User;
import com.nhg.game.infrastructure.networking.Client;
import com.nhg.game.infrastructure.networking.packet.ClientPacket;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;

import java.util.List;

@RequiredArgsConstructor
@IncomingPacket(header = InPacketHeader.MyRooms)
public class MyRooms implements ClientPacket<JSONObject> {

    private final ClientUserMap clientUserMap;
    private final RoomRepository roomRepository;
    private final RoomToJsonMapper roomToJsonMapper;

    @Override
    public void handle(Client<?> client, JSONObject body) {
        User user = clientUserMap.getUser(client.getId());

        if (user == null) return;

        List<Room> rooms = roomRepository.getRoomsByOwner(user);

        OutgoingPacket.send(
                client,
                OutPacketHeader.SendMyRooms,
                roomToJsonMapper.roomsToNavigatorRoomsJson(rooms)
        );
    }
}
