package com.nhg.game.application.usecase.item;

import com.nhg.common.domain.UseCase;
import com.nhg.game.application.repository.ItemRepository;
import com.nhg.game.domain.item.Item;
import com.nhg.game.domain.user.User;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;

import java.util.Collection;

@UseCase
@RequiredArgsConstructor
public class GetInventoryItemsUseCase {

    private final ItemRepository itemRepository;

    public Collection<Item> byOwner(@Nonnull User owner) {
        return itemRepository.getInventoryItemsByOwner(owner);
    }
}
