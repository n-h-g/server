package com.nhg.game.adapter.in.websocket.room;

import com.nhg.game.adapter.in.websocket.IncomingPacket;
import com.nhg.game.adapter.out.websocket.OutPacketHeaders;
import com.nhg.game.application.dto.CreateRoomRequest;
import com.nhg.game.application.usecase.room.CreateRoomUseCase;
import com.nhg.game.domain.room.Room;
import com.nhg.game.domain.user.User;
import com.nhg.game.infrastructure.context.BeanRetriever;
import com.nhg.game.infrastructure.networking.ClientUserMap;
import com.nhg.game.infrastructure.networking.OutgoingPacket;

public class CreateRoom extends IncomingPacket {

    private final ClientUserMap clientUserMap;
    private final CreateRoomUseCase createRoomUseCase;

    public CreateRoom() {
        clientUserMap = BeanRetriever.get(ClientUserMap.class);
        createRoomUseCase = BeanRetriever.get(CreateRoomUseCase.class);
    }

    @Override
    public void handle() throws Exception {
        User user = clientUserMap.get((String) client.getId());

        if (user == null) return;

        String name = body.getString("name");
        String desc = body.getString("desc");
        String layout = body.getString("layout");

        int doorX = body.getInt("door_x");
        int doorY = body.getInt("door_y");
        int doorDir = body.getInt("door_dir");
        int maxUsers = body.getInt("maxUsers");


        Room room = createRoomUseCase.createRoom(
                new CreateRoomRequest(name, desc, layout, user, doorX, doorY, doorDir, maxUsers)
        );

        if (room == null) return;

        client.sendMessage(new OutgoingPacket(OutPacketHeaders.SendRoomId, room.getId()));
    }
}

