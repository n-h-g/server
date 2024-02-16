package com.nhg.game.adapter.in.websocket.packet.room;

import com.nhg.game.adapter.in.websocket.ClientUserMap;
import com.nhg.game.adapter.in.websocket.IncomingPacket;
import com.nhg.game.adapter.out.websocket.OutPacketHeaders;
import com.nhg.game.adapter.out.websocket.OutgoingPacket;
import com.nhg.game.application.dto.CreateRoomRequest;
import com.nhg.game.application.usecase.room.CreateRoomUseCase;
import com.nhg.game.domain.room.Room;
import com.nhg.game.domain.user.User;
import com.nhg.game.infrastructure.networking.Client;
import com.nhg.game.infrastructure.networking.packet.ClientPacket;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;

@RequiredArgsConstructor
@IncomingPacket(header = 36)
public class CreateRoom implements ClientPacket<JSONObject> {

    private final ClientUserMap clientUserMap;
    private final CreateRoomUseCase createRoomUseCase;

    @Override
    public void handle(Client<?> client, JSONObject body) {
        User user = clientUserMap.getUser(client.getId());

        if (user == null) return;

        String name = body.getString("name");
        String desc = body.getString("desc");
        String layout = body.getString("layout");

        int doorX = body.getInt("door_x");
        int doorY = body.getInt("door_y");
        int doorDir = body.getInt("door_dir");
        int maxUsers = body.getInt("max_users");


        Room room = createRoomUseCase.createRoom(
                new CreateRoomRequest(name, desc, layout, user, doorX, doorY, doorDir, maxUsers)
        );

        if (room == null) return;

        OutgoingPacket.send(
                client,
                OutPacketHeaders.SendRoomId,
                room.getId()
        );
    }
}

