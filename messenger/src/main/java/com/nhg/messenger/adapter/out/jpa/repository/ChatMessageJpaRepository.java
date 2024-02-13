package com.nhg.messenger.adapter.out.jpa.repository;

import com.nhg.messenger.adapter.out.jpa.entity.ChatMessageJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMessageJpaRepository extends JpaRepository<ChatMessageJpa, Integer> {
}
