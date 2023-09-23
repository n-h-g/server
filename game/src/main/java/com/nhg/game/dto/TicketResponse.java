package com.nhg.game.dto;

import java.time.LocalDate;

public record TicketResponse (
    Integer id,

    String status,

    Integer senderId,

    Integer reportedId,

    Integer moderatorId,

    String message,

    Integer roomId,

    LocalDate submitDate
) {

}