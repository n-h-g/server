package com.nhg.game.shared;

import com.nhg.game.utils.Position3;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Getter
@Setter
@Embeddable
public class PersistentPosition {
    @Column(columnDefinition = "integer default 0")
    private int x;

    @Column(columnDefinition = "integer default 0")
    private int y;

    @Column(columnDefinition = "real default 0")
    private float z;

    public Position3 getPosition3() {
        return new Position3(x,y,z);
    }
}
