package com.nhg.moderation.service;

import com.nhg.moderation.model.Ticket;
import com.nhg.moderation.repository.TicketRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;

    public List<Ticket> searchBySenderId(Integer senderId) {
        return ticketRepository.findBySenderId(senderId);
    }
}
