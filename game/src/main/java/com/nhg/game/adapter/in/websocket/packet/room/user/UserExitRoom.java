package com.nhg.game.adapter.in.websocket.packet.room.user;

import com.nhg.game.adapter.in.websocket.ClientUserMap;
import com.nhg.game.adapter.in.websocket.IncomingPacket;
import com.nhg.game.adapter.out.websocket.OutPacketHeader;
import com.nhg.game.adapter.out.websocket.OutgoingPacket;
import com.nhg.game.application.usecase.room.FindRoomUseCase;
import com.nhg.game.application.usecase.room.user.UserExitRoomUseCase;
import com.nhg.game.domain.room.Room;
import com.nhg.game.domain.room.entity.Entity;
import com.nhg.game.domain.user.User;
import com.nhg.game.infrastructure.networking.Client;
import com.nhg.game.infrastructure.networking.packet.ClientPacket;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@IncomingPacket(header = 9)
public class UserExitRoom implements ClientPacket<JSONObject> {

    private final ClientUserMap clientUserMap;
    private final FindRoomUseCase findRoomUseCase;
    private final UserExitRoomUseCase exitRoomUseCase;

    @Override
    public void handle(Client<?> client, JSONObject body) {
        User user = clientUserMap.getUser(client.getId());

        if (user == null) return;

        int roomId = body.getInt("id");

        Optional<Room> optRoom = findRoomUseCase.activeById(roomId);

        if(optRoom.isEmpty()) {
            log.debug("User "+ user.getId() + " exited room with id " + roomId + " but it doesn't exist.");
            return;
        }

        Room room = optRoom.get();

        Entity entity = exitRoomUseCase.userExitRoom(user, room);

        if (entity == null) return;

        OutgoingPacket.send(
                room.getEntities().getUsers(),
                OutPacketHeader.RemoveRoomEntity,
                entity.getId().toString()
        );
    }

}
