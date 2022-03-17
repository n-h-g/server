package com.cubs3d.game.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@AllArgsConstructor
public class Int2 {

    private int x;
    private int y;

    @Override
    public String toString() {
        return "("+ x +";"+ y +")";
    }
}
