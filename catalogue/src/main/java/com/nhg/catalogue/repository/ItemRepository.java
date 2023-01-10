package com.nhg.catalogue.repository;

import com.nhg.catalogue.model.Item;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends CrudRepository<Item, Integer> {

    @Query(value = "SELECT * FROM item WHERE page_id = :pageId", nativeQuery = true)
    List<Item> findByPageId(@Param("pageId") int pageId);
}
