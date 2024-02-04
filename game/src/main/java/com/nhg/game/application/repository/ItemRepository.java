package com.nhg.game.application.repository;

import com.nhg.game.domain.item.Item;
import com.nhg.game.domain.user.User;

import java.util.List;
import java.util.Optional;

public interface ItemRepository {

    List<Item> getInventoryItemsByOwner(User owner);

    Optional<Item> findItemById(int itemId);
}
