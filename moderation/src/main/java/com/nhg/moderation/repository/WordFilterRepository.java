package com.nhg.moderation.repository;

import com.nhg.moderation.model.WordFilter;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WordFilterRepository extends CrudRepository<WordFilter, String> {

}
