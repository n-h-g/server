package com.nhg.game.adapter.in.websocket.packet.room.item;

import com.nhg.game.adapter.in.websocket.ClientUserMap;
import com.nhg.game.adapter.in.websocket.mapper.EntityToJsonMapper;
import com.nhg.game.adapter.in.websocket.packet.IncomingPacket;
import com.nhg.game.adapter.out.websocket.OutPacketHeaders;
import com.nhg.game.adapter.out.websocket.OutgoingPacket;
import com.nhg.game.application.repository.UserEntityRepository;
import com.nhg.game.application.usecase.room.item.PlaceItemUseCase;
import com.nhg.game.domain.room.Room;
import com.nhg.game.domain.room.entity.Entity;
import com.nhg.game.domain.user.User;
import com.nhg.game.infrastructure.context.BeanRetriever;

import java.util.Optional;

public class PlaceItem extends IncomingPacket {

    private final ClientUserMap clientUserMap;
    private final PlaceItemUseCase placeItemUseCase;
    private final EntityToJsonMapper entityToJsonMapper;
    private final UserEntityRepository userEntityRepository;

    public PlaceItem() {
        clientUserMap = BeanRetriever.get(ClientUserMap.class);
        placeItemUseCase = BeanRetriever.get(PlaceItemUseCase.class);
        entityToJsonMapper = BeanRetriever.get(EntityToJsonMapper.class);
        userEntityRepository = BeanRetriever.get(UserEntityRepository.class);
    }

    @Override
    public void handle() throws Exception {
        User user = clientUserMap.getUser(client.getId());

        if (user == null) return;

        Optional<Entity> entityOpt = userEntityRepository.findEntityByUserId(user.getId());

        if (entityOpt.isEmpty()) return;

        Entity userEntity = entityOpt.get();
        Room room = userEntity.getRoom();

        int itemId = body.getInt("id");
        int x = body.getInt("x");
        int y = body.getInt("y");
        float z = body.getFloat("z"); // TODO: Remove it

        Entity itemEntity = placeItemUseCase.placeItem(room, userEntity, itemId, x, y, z);
        if (itemEntity == null) return;

        OutgoingPacket.send(
                room.getEntities().getUsers(),
                OutPacketHeaders.AddRoomEntity,
                entityToJsonMapper.entityToJson(itemEntity)
        );

    }
}
