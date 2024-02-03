package com.nhg.game.application.repository;

import com.nhg.game.domain.item.ItemPrototype;

import java.util.Optional;

public interface ItemPrototypeRepository {

    Optional<ItemPrototype> findByName(String name);

}
