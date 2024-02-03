package com.nhg.game.domain.item;

import com.nhg.game.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Item {

    protected Integer id;
    protected ItemPrototype prototype;
    protected User owner;
}
