package com.nhg.game.adapter.in.websocket.packet.room.user;

import com.nhg.game.adapter.in.websocket.ClientUserMap;
import com.nhg.game.adapter.in.websocket.IncomingPacket;
import com.nhg.game.application.repository.UserEntityRepository;
import com.nhg.game.application.usecase.room.entity.EntityMoveToUseCase;
import com.nhg.game.domain.room.entity.Entity;
import com.nhg.game.domain.shared.position.Position2;
import com.nhg.game.domain.user.User;
import com.nhg.game.infrastructure.networking.Client;
import com.nhg.game.infrastructure.networking.packet.ClientPacket;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;

import java.util.Optional;

@RequiredArgsConstructor
@IncomingPacket(header = 10)
public class UserMove implements ClientPacket<JSONObject> {

    private final ClientUserMap clientUserMap;
    private final UserEntityRepository userEntityRepository;
    private final EntityMoveToUseCase entityMoveToUseCase;

    @Override
    public void handle(Client<?> client, JSONObject body) {
        User user = clientUserMap.getUser(client.getId());

        if (user == null) return;

        Optional<Entity> entityOpt = userEntityRepository.findEntityByUserId(user.getId());

        if (entityOpt.isEmpty()) return;

        Position2 destination = new Position2(body.getInt("x"), body.getInt("y"));

        Entity entity = entityOpt.get();

        entityMoveToUseCase.moveTo(entity, destination);
    }
}
