package com.nhg.game.item;

import org.springframework.data.repository.CrudRepository;

public interface ItemSpecificationRepository extends CrudRepository<ItemSpecification, Integer> {
    ItemSpecification findByName(String name);
}
