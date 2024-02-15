package com.nhg.game.adapter.in.websocket.packet.room.user;

import com.nhg.game.adapter.in.websocket.ClientUserMap;
import com.nhg.game.adapter.in.websocket.packet.IncomingPacket;
import com.nhg.game.application.repository.UserEntityRepository;
import com.nhg.game.application.usecase.room.entity.EntityMoveToUseCase;
import com.nhg.game.domain.room.entity.Entity;
import com.nhg.game.domain.shared.position.Position2;
import com.nhg.game.domain.user.User;
import com.nhg.game.infrastructure.context.BeanRetriever;

import java.util.Optional;

public class UserMove extends IncomingPacket  {

    private final ClientUserMap clientUserMap;
    private final UserEntityRepository userEntityRepository;
    private final EntityMoveToUseCase entityMoveToUseCase;

    public UserMove() {
        clientUserMap = BeanRetriever.get(ClientUserMap.class);
        userEntityRepository = BeanRetriever.get(UserEntityRepository.class);
        entityMoveToUseCase = BeanRetriever.get(EntityMoveToUseCase.class);
    }

    @Override
    public void handle() throws Exception {
        User user = clientUserMap.getUser(client.getId());

        if (user == null) return;

        Optional<Entity> entityOpt = userEntityRepository.findEntityByUserId(user.getId());

        if (entityOpt.isEmpty()) return;

        Position2 destination = new Position2(body.getInt("x"), body.getInt("y"));

        Entity entity = entityOpt.get();

        entityMoveToUseCase.moveTo(entity, destination);
    }
}
