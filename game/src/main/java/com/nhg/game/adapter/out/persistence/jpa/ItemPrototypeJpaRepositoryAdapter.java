package com.nhg.game.adapter.out.persistence.jpa;

import com.nhg.game.adapter.out.persistence.jpa.item.ItemPrototypeJpa;
import com.nhg.game.adapter.out.persistence.jpa.repository.ItemPrototypeJpaRepository;
import com.nhg.game.application.repository.ItemPrototypeRepository;
import com.nhg.game.domain.item.ItemPrototype;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ItemPrototypeJpaRepositoryAdapter implements ItemPrototypeRepository {

    private final ItemPrototypeJpaRepository itemPrototypeJpaRepository;

    @Override
    public Optional<ItemPrototype> findByName(String name) {
        return itemPrototypeJpaRepository.findByName(name)
                .map(ItemPrototypeJpa::toItemPrototype);
    }
}
