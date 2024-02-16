package com.nhg.game.adapter.in.websocket.packet.room.item;

import com.nhg.game.adapter.in.websocket.ClientUserMap;
import com.nhg.game.adapter.in.websocket.IncomingPacket;
import com.nhg.game.adapter.in.websocket.mapper.EntityToJsonMapper;
import com.nhg.game.adapter.out.websocket.OutPacketHeaders;
import com.nhg.game.adapter.out.websocket.OutgoingPacket;
import com.nhg.game.application.repository.UserEntityRepository;
import com.nhg.game.application.usecase.room.item.PlaceItemUseCase;
import com.nhg.game.domain.room.Room;
import com.nhg.game.domain.room.entity.Entity;
import com.nhg.game.domain.shared.position.Position2;
import com.nhg.game.domain.user.User;
import com.nhg.game.infrastructure.networking.Client;
import com.nhg.game.infrastructure.networking.packet.ClientPacket;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;

import java.util.Optional;

@RequiredArgsConstructor
@IncomingPacket(header = 25)
public class PlaceItem implements ClientPacket<JSONObject> {

    private final ClientUserMap clientUserMap;
    private final PlaceItemUseCase placeItemUseCase;
    private final EntityToJsonMapper entityToJsonMapper;
    private final UserEntityRepository userEntityRepository;

    @Override
    public void handle(Client<?> client, JSONObject body) {
        User user = clientUserMap.getUser(client.getId());

        if (user == null) return;

        Optional<Entity> entityOpt = userEntityRepository.findEntityByUserId(user.getId());

        if (entityOpt.isEmpty()) return;

        Entity userEntity = entityOpt.get();
        Room room = userEntity.getRoom();

        int itemId = body.getInt("id");
        Position2 position = new Position2(body.getInt("x"), body.getInt("y"));

        Entity itemEntity = placeItemUseCase.placeItem(room, userEntity, itemId, position);
        if (itemEntity == null) return;

        OutgoingPacket.send(
                room.getEntities().getUsers(),
                OutPacketHeaders.AddRoomEntity,
                entityToJsonMapper.entityToJson(itemEntity)
        );

    }
}
