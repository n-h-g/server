package com.nhg.game.domain.item;

import com.nhg.game.domain.room.entity.Entity;
import com.nhg.game.domain.shared.position.Position3;
import com.nhg.game.domain.shared.position.Rotation;
import com.nhg.game.domain.user.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomItem extends Item {

    private Entity entity;

    private Rotation rotation;
    private Position3 position;
    private String extraData;

    public RoomItem(Integer id, ItemPrototype prototype, User owner) {
        super(id, prototype, owner);
    }

    public RoomItem(Integer id, ItemPrototype prototype, User owner, Rotation rotation,
                    Position3 position, String extraData) {
        super(id, prototype, owner);

        this.rotation = rotation;
        this.position = position;
        this.extraData = extraData;
    }
}
