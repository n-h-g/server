package com.nhg.game.room.entity.component;

import com.nhg.game.networking.message.outgoing.JsonSerializable;
import com.nhg.game.room.entity.Entity;

public abstract class Component implements JsonSerializable {

    protected Entity entity;

    public void setEntity(Entity entity) {
        if (this.entity != null) return;

        this.entity = entity;
    }

    public abstract void cycle();
}
