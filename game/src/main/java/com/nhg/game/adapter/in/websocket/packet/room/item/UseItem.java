package com.nhg.game.adapter.in.websocket.packet.room.item;

import com.nhg.game.adapter.in.InPacketHeader;
import com.nhg.game.adapter.in.websocket.ClientUserMap;
import com.nhg.game.adapter.in.websocket.IncomingPacket;
import com.nhg.game.application.repository.UserEntityRepository;
import com.nhg.game.application.usecase.room.item.UseItemUseCase;
import com.nhg.game.domain.room.Room;
import com.nhg.game.domain.room.entity.Entity;
import com.nhg.game.domain.user.User;
import com.nhg.game.infrastructure.networking.Client;
import com.nhg.game.infrastructure.networking.packet.ClientPacket;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@IncomingPacket(header = InPacketHeader.UseItem)
public class UseItem implements ClientPacket<JSONObject> {

    private final ClientUserMap clientUserMap;
    private final UseItemUseCase useItemUseCase;
    private final UserEntityRepository userEntityRepository;

    @Override
    public void handle(Client<?> client, JSONObject body) {
        User user = clientUserMap.getUser(client.getId());

        if (user == null) return;

        Optional<Entity> entityOpt = userEntityRepository.findEntityByUserId(user.getId());

        if (entityOpt.isEmpty()) return;

        Entity userEntity = entityOpt.get();
        Room room = userEntity.getRoom();

        UUID itemEntityId = UUID.fromString(body.getString("id"));

        Entity itemEntity = room.getEntities().getEntity(itemEntityId);

        if (itemEntity == null) return;

        useItemUseCase.useItem(userEntity, itemEntity, room);
    }
}