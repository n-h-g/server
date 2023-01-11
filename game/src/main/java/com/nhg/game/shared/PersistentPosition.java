package com.nhg.game.shared;

import com.nhg.game.utils.Int3;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Setter
@Embeddable
public class PersistentPosition {
    @Column(columnDefinition = "integer default 0")
    private int x;

    @Column(columnDefinition = "integer default 0")
    private int y;

    @Column(columnDefinition = "integer default 0")
    private int z;

    public Int3 getInt3Position() {
        return new Int3(x,y,z);
    }
}