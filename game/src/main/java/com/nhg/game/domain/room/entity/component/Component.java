package com.nhg.game.domain.room.entity.component;


import com.fasterxml.jackson.databind.JsonSerializable;
import com.nhg.game.domain.room.entity.Entity;

public abstract class Component implements JsonSerializable {

    protected Entity entity;

    public void setEntity(Entity entity) {
        if (this.entity != null) return;

        this.entity = entity;
    }

    public abstract void cycle();
}
