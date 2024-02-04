package com.nhg.game.adapter.in.websocket.packet.room.item;

import com.nhg.game.adapter.in.websocket.ClientUserMap;
import com.nhg.game.adapter.in.websocket.packet.IncomingPacket;
import com.nhg.game.application.repository.UserEntityRepository;
import com.nhg.game.application.usecase.room.item.PickUpItemUseCase;
import com.nhg.game.domain.room.Room;
import com.nhg.game.domain.room.entity.Entity;
import com.nhg.game.domain.user.User;
import com.nhg.game.infrastructure.context.BeanRetriever;

import java.util.Optional;
import java.util.UUID;

public class PickUpItem extends IncomingPacket {

    private final ClientUserMap clientUserMap;
    private final UserEntityRepository userEntityRepository;
    private final PickUpItemUseCase pickUpItemUseCase;


    public PickUpItem() {
        clientUserMap = BeanRetriever.get(ClientUserMap.class);
        userEntityRepository = BeanRetriever.get(UserEntityRepository.class);
        pickUpItemUseCase = BeanRetriever.get(PickUpItemUseCase.class);
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

        pickUpItemUseCase.pickUpItem(userEntity, itemEntity, room);
    }
}
