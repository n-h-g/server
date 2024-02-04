package com.nhg.game.adapter.in.websocket.packet.room.user;

import com.nhg.game.adapter.in.websocket.ClientUserMap;
import com.nhg.game.adapter.in.websocket.mapper.EntityToJsonMapper;
import com.nhg.game.adapter.in.websocket.mapper.RoomToJsonMapper;
import com.nhg.game.adapter.in.websocket.packet.IncomingPacket;
import com.nhg.game.adapter.out.websocket.OutPacketHeaders;
import com.nhg.game.adapter.out.websocket.OutgoingPacket;
import com.nhg.game.application.repository.UserEntityRepository;
import com.nhg.game.application.usecase.room.FindRoomUseCase;
import com.nhg.game.application.usecase.room.user.UserEnterRoomUseCase;
import com.nhg.game.application.usecase.room.user.UserExitRoomUseCase;
import com.nhg.game.domain.room.Room;
import com.nhg.game.domain.room.entity.Entity;
import com.nhg.game.domain.user.User;
import com.nhg.game.infrastructure.context.BeanRetriever;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Optional;

@Slf4j
public class UserEnterRoom extends IncomingPacket {

    private final ClientUserMap clientUserMap;
    private final FindRoomUseCase findRoomUseCase;
    private final UserEnterRoomUseCase enterRoomUseCase;
    private final UserExitRoomUseCase exitRoomUseCase;
    private final UserEntityRepository userEntityRepository;
    private final EntityToJsonMapper entityMapper;
    private final RoomToJsonMapper roomMapper;

    public UserEnterRoom() {
        clientUserMap = BeanRetriever.get(ClientUserMap.class);
        findRoomUseCase = BeanRetriever.get(FindRoomUseCase.class);
        enterRoomUseCase = BeanRetriever.get(UserEnterRoomUseCase.class);
        exitRoomUseCase = BeanRetriever.get(UserExitRoomUseCase.class);
        userEntityRepository = BeanRetriever.get(UserEntityRepository.class);
        entityMapper = BeanRetriever.get(EntityToJsonMapper.class);
        roomMapper = BeanRetriever.get(RoomToJsonMapper.class);
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
        Collection<User> roomUsers = room.getEntities().getUsers();

        Optional<Entity> userEntityOpt = userEntityRepository.findEntityByUserId(user.getId());

        // the user already had an entity in the room, remove it before entering.
        if (userEntityOpt.isPresent()) {
            OutgoingPacket.send(
                    roomUsers,
                    OutPacketHeaders.RemoveRoomEntity,
                    entityMapper.entityToJson(userEntityOpt.get())
            );

            exitRoomUseCase.userExitRoom(user, room);
        }

        room = enterRoomUseCase.userEnterRoom(user, room);

        //  load the room for the user who just entered.
        {
            OutgoingPacket.send(
                    client,
                    OutPacketHeaders.SendRoomData,
                    roomMapper.roomToJson(room)
            );

            Collection<Entity> roomEntities = room.getEntities().values();

            OutgoingPacket.send(
                    client,
                    OutPacketHeaders.LoadRoomEntities,
                    entityMapper.entitiesToJson(roomEntities)
            );

        }

        // add the new entity to all clients that were already in the room.
        {
            Entity userEntity = userEntityRepository.findEntityByUserId(user.getId()).get();

            OutgoingPacket.send(
                    roomUsers,
                    OutPacketHeaders.AddRoomEntity,
                    entityMapper.entityToJson(userEntity)
            );
        }

    }
}
