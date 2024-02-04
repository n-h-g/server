package com.nhg.game.domain.room.entity.component;

import com.nhg.game.domain.room.entity.Entity;

public abstract class Component {

    protected Entity entity;

    public void setEntity(Entity entity) {
        if (this.entity != null) return;

        this.entity = entity;
    }

    public void cycle() {};
}
