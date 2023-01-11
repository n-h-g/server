package com.nhg.game.item;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemSpecificationRepository extends CrudRepository<ItemSpecification, Integer> {
    ItemSpecification findByName(String name);
}
