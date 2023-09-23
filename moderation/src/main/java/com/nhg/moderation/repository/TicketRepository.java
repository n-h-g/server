package com.nhg.moderation.repository;

import com.nhg.moderation.model.Ticket;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends CrudRepository<Ticket, Integer> {
    List<Ticket> findBySenderId(Integer id);
}
