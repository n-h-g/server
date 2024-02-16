package com.nhg.game.adapter.in.websocket.packet.navigator;

import com.nhg.game.adapter.in.websocket.IncomingPacket;
import com.nhg.game.adapter.in.websocket.mapper.RoomToJsonMapper;
import com.nhg.game.adapter.out.websocket.OutPacketHeader;
import com.nhg.game.adapter.out.websocket.OutgoingPacket;
import com.nhg.game.application.repository.ActiveRoomRepository;
import com.nhg.game.domain.room.Room;
import com.nhg.game.infrastructure.networking.Client;
import com.nhg.game.infrastructure.networking.packet.ClientPacket;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;

import java.util.List;

@RequiredArgsConstructor
@IncomingPacket(header = 6)
public class AllRooms implements ClientPacket<JSONObject> {

    private final ActiveRoomRepository activeRoomRepository;
    private final RoomToJsonMapper roomToJsonMapper;


    @Override
    public void handle(Client<?> client, JSONObject body) {
        List<Room> rooms = activeRoomRepository.getAll();

        OutgoingPacket.send(
                client,
                OutPacketHeader.SendAllRooms,
                roomToJsonMapper.roomsToNavigatorRoomsJson(rooms)
        );
    }
}
