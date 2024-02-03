package com.nhg.game.adapter.in.websocket.packet.room.user;

import com.nhg.game.adapter.in.websocket.ClientUserMap;
import com.nhg.game.adapter.in.websocket.IncomingPacket;
import com.nhg.game.adapter.out.websocket.OutPacketHeaders;
import com.nhg.game.adapter.out.websocket.OutgoingPacket;
import com.nhg.game.application.usecase.room.FindRoomUseCase;
import com.nhg.game.application.usecase.room.user.UserExitRoomUseCase;
import com.nhg.game.domain.room.Room;
import com.nhg.game.domain.room.entity.Entity;
import com.nhg.game.domain.user.User;
import com.nhg.game.infrastructure.context.BeanRetriever;
import com.nhg.game.infrastructure.helper.BroadcastHelper;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public class UserExitRoom extends IncomingPacket {

    private final ClientUserMap clientUserMap;
    private final FindRoomUseCase findRoomUseCase;
    private final UserExitRoomUseCase exitRoomUseCase;

    public UserExitRoom() {
        clientUserMap = BeanRetriever.get(ClientUserMap.class);
        findRoomUseCase = BeanRetriever.get(FindRoomUseCase.class);
        exitRoomUseCase = BeanRetriever.get(UserExitRoomUseCase.class);
    }

    @Override
    public void handle() throws Exception {
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

        BroadcastHelper.sendBroadcastMessage(room.getUsers().values(), new OutgoingPacket(
                OutPacketHeaders.RemoveRoomEntity,
                entity.getId().toString()
        ));
    }

}
