package com.nhg.moderation.controller;

import com.nhg.moderation.model.Ticket;
import com.nhg.moderation.service.TicketService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("api/v1/moderation/tickets")
public class TicketController {
    private final TicketService ticketService;

    @GetMapping("{senderId}")
    public List<Ticket> searchBySenderId(@PathVariable("senderId") Integer senderId) {
        return ticketService.searchBySenderId(senderId);
    }
}
