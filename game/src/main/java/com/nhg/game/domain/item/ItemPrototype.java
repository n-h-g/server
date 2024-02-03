package com.nhg.game.domain.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ItemPrototype {

    private Integer id;
    private ItemType itemType;
    private String name;
    private String interaction;
    private short stateCount;
    private short width;
    private short length;
    private float height;
    private boolean allowStack;
    private boolean allowWalk;
    private boolean allowInventoryStack;
    private boolean allowSit;
    private boolean allowLay;

}
