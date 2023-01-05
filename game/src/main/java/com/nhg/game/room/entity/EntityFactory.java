package com.nhg.game.room.entity;

import com.nhg.game.room.Room;
import com.nhg.game.room.entity.component.ComponentType;
import com.nhg.game.utils.Int3;
import com.nhg.game.utils.Rotation;
import org.springframework.data.util.Pair;

import java.util.UUID;

public class EntityFactory {

    public static Entity create(EntityType type, Room room) {
        UUID id = UUID.randomUUID();
        Entity entity = new Entity(id, type, room);

        switch (type) {
            case HUMAN -> entity
                    .addComponent(ComponentType.Position, Pair.of(room.getRoomLayout().getInt3AtDoor(), Int3.class))
                    .addComponent(ComponentType.Movement)
                    .addComponent(ComponentType.BodyHeadRotation, Pair.of(room.getRoomLayout().getDoorRotation(), Rotation.class));

            case ITEM -> entity.addComponent(ComponentType.Position);
        }

        return entity;
    }
}