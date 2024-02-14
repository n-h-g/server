package com.nhg.game.adapter.in.websocket.packet.room.item;

import com.nhg.game.adapter.in.websocket.ClientUserMap;
import com.nhg.game.adapter.in.websocket.packet.IncomingPacket;
import com.nhg.game.application.repository.UserEntityRepository;
import com.nhg.game.application.usecase.room.item.UseItemUseCase;
import com.nhg.game.domain.room.Room;
import com.nhg.game.domain.room.entity.Entity;
import com.nhg.game.domain.user.User;
import com.nhg.game.infrastructure.context.BeanRetriever;

import java.util.Optional;
import java.util.UUID;

public class UseItem extends IncomingPacket {

    private final ClientUserMap clientUserMap;
    private final UseItemUseCase useItemUseCase;
    private final UserEntityRepository userEntityRepository;

    public UseItem() {
        clientUserMap = BeanRetriever.get(ClientUserMap.class);
        useItemUseCase = BeanRetriever.get(UseItemUseCase.class);
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

        UUID itemEntityId = UUID.fromString(body.getString("id"));

        Entity itemEntity = room.getEntities().getEntity(itemEntityId);

        if (itemEntity == null) return;

        useItemUseCase.useItem(userEntity, itemEntity, room);
    }
}