package com.nhg.game.adapter.in.websocket.room.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhg.game.adapter.in.websocket.IncomingPacket;
import com.nhg.game.adapter.out.websocket.OutPacketHeaders;
import com.nhg.game.application.dto.RoomResponse;
import com.nhg.game.application.usecase.room.FindRoomUseCase;
import com.nhg.game.application.usecase.room.user.UserEnterRoomUseCase;
import com.nhg.game.application.usecase.room.user.UserExitRoomUseCase;
import com.nhg.game.domain.room.Room;
import com.nhg.game.domain.room.entity.Entity;
import com.nhg.game.domain.user.User;
import com.nhg.game.infrastructure.context.BeanRetriever;
import com.nhg.game.infrastructure.helper.BroadcastHelper;
import com.nhg.game.infrastructure.networking.ClientUserMap;
import com.nhg.game.infrastructure.networking.OutgoingPacket;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Optional;

@Slf4j
public class UserEnterRoom extends IncomingPacket {

    private final ClientUserMap clientUserMap;
    private final FindRoomUseCase findRoomUseCase;
    private final UserEnterRoomUseCase enterRoomUseCase;
    private final UserExitRoomUseCase exitRoomUseCase;
    private final ObjectMapper objectMapper;

    public UserEnterRoom() {
        clientUserMap = BeanRetriever.get(ClientUserMap.class);
        findRoomUseCase = BeanRetriever.get(FindRoomUseCase.class);
        enterRoomUseCase = BeanRetriever.get(UserEnterRoomUseCase.class);
        exitRoomUseCase = BeanRetriever.get(UserExitRoomUseCase.class);
        objectMapper = BeanRetriever.get(ObjectMapper.class);
    }

    @Override
    public void handle() throws Exception {
        User user = clientUserMap.getUser(client.getId());

        if (user == null) return;

        int roomId = body.getInt("id");

        Optional<Room> optRoom = findRoomUseCase.activeById(roomId);

        if (optRoom.isEmpty()) {
            optRoom = findRoomUseCase.byId(roomId);

            if (optRoom.isEmpty()) {
                log.debug("User " + user.getId() + " entered room with id " + roomId + " but it doesn't exist.");
                return;
            }
        }

        Room room = optRoom.get();

        // users who were already in the room.
        Collection<User> roomUsers = room.getUsers().values();

        Entity userEntity = room.getUserEntities().get(user.getId());

        // the user already had an entity in the room, remove it before entering.
        if (userEntity != null) {
            BroadcastHelper.sendBroadcastMessage(roomUsers, new OutgoingPacket(
                    OutPacketHeaders.RemoveRoomEntity,
                    objectMapper.writeValueAsString(userEntity)
            ));

            exitRoomUseCase.userExitRoom(user);
        }

        room = enterRoomUseCase.userEnterRoom(user, room);

        //  load the room for the user who just entered.
        {

            client.sendMessage(new OutgoingPacket(
                    OutPacketHeaders.SendRoomData,
                    objectMapper.writeValueAsString(RoomResponse.fromDomain(room))
            ));

            Collection<Entity> roomEntities = room.getEntities().values();

            client.sendMessage(new OutgoingPacket(
                    OutPacketHeaders.LoadRoomEntities,
                    objectMapper.writeValueAsString(roomEntities)
            ));
        }

        // add the new entity to all clients that were already in the room.
        {
            userEntity = room.getUserEntities().get(user.getId());

            BroadcastHelper.sendBroadcastMessage(roomUsers, new OutgoingPacket(
                    OutPacketHeaders.AddRoomEntity,
                    objectMapper.writeValueAsString(userEntity)
            ));
        }

    }
}
