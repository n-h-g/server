package com.nhg.game.application.repository;

import com.nhg.game.domain.item.Item;
import com.nhg.game.domain.item.RoomItem;
import com.nhg.game.domain.shared.position.Position2;
import com.nhg.game.domain.user.User;

import java.util.Collection;
import java.util.Optional;

public interface ItemRepository {

    void save(RoomItem item);

    Collection<Item> getInventoryItemsByOwner(User owner);

    Collection<RoomItem> getItemsByRoomId(int roomId);

    Optional<Item> findItemById(int itemId);

    void unsetRoomForItem(RoomItem item);

    Collection<RoomItem> getRoomItemsAtPosition(int roomId, Position2 position);

    RoomItem getHighestItemAtPosition(int roomId, Position2 position);


}
