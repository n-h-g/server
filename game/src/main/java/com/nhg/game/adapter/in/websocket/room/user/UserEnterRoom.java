package com.nhg.game.adapter.in.websocket.room.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhg.game.adapter.in.websocket.IncomingPacket;
import com.nhg.game.adapter.out.websocket.OutPacketHeaders;
import com.nhg.game.application.dto.RoomResponse;
import com.nhg.game.application.repository.ActiveRoomRepository;
import com.nhg.game.application.usecase.room.user.UserEnterRoomUseCase;
import com.nhg.game.application.usecase.room.user.UserExitRoomUseCase;
import com.nhg.game.domain.room.Room;
import com.nhg.game.domain.user.User;
import com.nhg.game.infrastructure.context.BeanRetriever;
import com.nhg.game.infrastructure.helper.BroadcastHelper;
import com.nhg.game.infrastructure.networking.ClientUserMap;
import com.nhg.game.infrastructure.networking.OutgoingPacket;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserEnterRoom extends IncomingPacket {

    private final ClientUserMap clientUserMap;
    private final UserEnterRoomUseCase enterRoomUseCase;
    private final UserExitRoomUseCase exitRoomUseCase;
    private final ActiveRoomRepository activeRoomRepository;
    private final ObjectMapper objectMapper;

    public UserEnterRoom() {
        clientUserMap = BeanRetriever.get(ClientUserMap.class);
        enterRoomUseCase = BeanRetriever.get(UserEnterRoomUseCase.class);
        exitRoomUseCase = BeanRetriever.get(UserExitRoomUseCase.class);
        activeRoomRepository = BeanRetriever.get(ActiveRoomRepository.class);
        objectMapper = BeanRetriever.get(ObjectMapper.class);

    }

    @Override
    public void handle() throws Exception {
        User user = clientUserMap.getUser(client.getId());

        if (user == null) return;

        int roomId = body.getInt("id");

        if (user.getEntity() != null) {
            BroadcastHelper.sendBroadcastMessage(user.getEntity().getRoom().getUsers().values(),
                    new OutgoingPacket(OutPacketHeaders.RemoveRoomEntity, user.getEntity().getId().toString()));

            exitRoomUseCase.userExitRoom(user);
        }

        Room room = enterRoomUseCase.userEnterRoom(user, roomId);

        if (room == null) {
            log.debug("User "+ user.getId() + " entered room with id " + roomId + " but it doesn't exist.");
            return;
        }

        client.sendMessage(new OutgoingPacket(
                OutPacketHeaders.SendRoomData,
                objectMapper.writeValueAsString(RoomResponse.fromDomain(room))
        ));

        // TODO test object mapper for entities

        client.sendMessage(new OutgoingPacket(
                OutPacketHeaders.LoadRoomEntities,
                objectMapper.writeValueAsString(room.getEntities().values())
        ));

        BroadcastHelper.sendBroadcastMessage(user.getEntity().getRoom().getUsers().values(),
                new OutgoingPacket(
                        OutPacketHeaders.AddRoomEntity,
                        objectMapper.writeValueAsString(user.getEntity())
                )
        );
    }
}
