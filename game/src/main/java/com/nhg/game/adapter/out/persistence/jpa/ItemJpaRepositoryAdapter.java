package com.nhg.game.adapter.out.persistence.jpa;

import com.nhg.game.adapter.out.persistence.jpa.item.ItemJpa;
import com.nhg.game.adapter.out.persistence.jpa.repository.ItemJpaRepository;
import com.nhg.game.application.repository.ItemRepository;
import com.nhg.game.domain.item.Item;
import com.nhg.game.domain.user.User;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ItemJpaRepositoryAdapter implements ItemRepository {

    private final ItemJpaRepository itemJpaRepository;

    @Override
    public List<Item> getInventoryItemsByOwner(@NonNull User owner) {
        return itemJpaRepository.inventoryItemsByOwnerId(owner.getId())
                .stream().map(ItemJpa::toItem).toList();
    }

    @Override
    public Optional<Item> findItemById(int itemId) {
        return itemJpaRepository.findById(itemId)
                .map(ItemJpa::toItem);
    }
}
