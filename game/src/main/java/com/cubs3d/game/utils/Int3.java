package com.cubs3d.game.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Int3 {

    private int x;
    private int y;
    private int z;

    public Int2 ToInt2XY() {
        return new Int2(x,y);
    }
}
