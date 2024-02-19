package com.nhg.game.adapter.out.persistence;

import com.nhg.game.adapter.out.persistence.inmemory.RoomItemInMemoryCache;
import com.nhg.game.adapter.out.persistence.jpa.item.ItemJpa;
import com.nhg.game.adapter.out.persistence.jpa.repository.ItemJpaRepository;
import com.nhg.game.application.repository.ItemRepository;
import com.nhg.game.domain.item.Item;
import com.nhg.game.domain.item.RoomItem;
import com.nhg.game.domain.shared.position.Position2;
import com.nhg.game.domain.user.User;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ItemRepositoryAdapter implements ItemRepository {

    private final ItemJpaRepository itemJpaRepository;
    private final RoomItemInMemoryCache roomItemCache;

    @Override
    public void save(RoomItem item) {
        ItemJpa itemJpa = itemJpaRepository.findById(item.getId())
                .orElseGet(() -> ItemJpa.fromDomain(item));

        roomItemCache.addItem(item.getEntity().getRoom().getId(), item);
        itemJpaRepository.save(itemJpa);
    }

    @Override
    public Collection<Item> getInventoryItemsByOwner(@NonNull User owner) {
        return itemJpaRepository.inventoryItemsByOwnerId(owner.getId())
                .stream().map(ItemJpa::toItem).toList();
    }

    @Override
    public Optional<Item> findItemById(int itemId) {
        return itemJpaRepository.findById(itemId)
                .map(ItemJpa::toItem);
    }

    @Override
    public void unsetRoomForItem(RoomItem item) {
        roomItemCache.removeItem(item.getEntity().getRoom().getId(), item);
        itemJpaRepository.unsetRoomForItem(item.getId());
    }

    @Override
    public Collection<RoomItem> getRoomItemsAtPosition(int roomId, Position2 position) {
        return roomItemCache.getItemsAt(roomId, position);
    }

    @Override
    public RoomItem getHighestItemAtPosition(int roomId, Position2 position) {
        return roomItemCache.getHighestItemAt(roomId, position);
    }
}
