package com.nhg.game.adapter.in.websocket.packet.room.item;

import com.nhg.game.adapter.in.websocket.ClientUserMap;
import com.nhg.game.adapter.in.websocket.IncomingPacket;
import com.nhg.game.application.repository.UserEntityRepository;
import com.nhg.game.application.usecase.room.item.MoveItemUseCase;
import com.nhg.game.domain.room.Room;
import com.nhg.game.domain.room.entity.Entity;
import com.nhg.game.domain.shared.position.Position2;
import com.nhg.game.domain.user.User;
import com.nhg.game.infrastructure.networking.Client;
import com.nhg.game.infrastructure.networking.packet.ClientPacket;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@IncomingPacket(header = 26)
public class MoveItem implements ClientPacket<JSONObject> {

    private final ClientUserMap clientUserMap;
    private final UserEntityRepository userEntityRepository;
    private final MoveItemUseCase moveItemUseCase;


    @Override
    public void handle(Client<?> client, JSONObject body) {
        User user = clientUserMap.getUser(client.getId());

        if (user == null) return;

        Optional<Entity> entityOpt = userEntityRepository.findEntityByUserId(user.getId());

        if (entityOpt.isEmpty()) return;

        Entity userEntity = entityOpt.get();
        Room room = userEntity.getRoom();

        UUID itemEntityId = UUID.fromString(body.getString("id"));
        Position2 position = new Position2(body.getInt("x"), body.getInt("y"));

        Entity itemEntity = room.getEntities().getEntity(itemEntityId);

        if (itemEntity == null) return;

        moveItemUseCase.moveItem(userEntity, itemEntity, room, position);
    }
}
