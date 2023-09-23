package com.nhg.moderation.model;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(
            generator = "sequence_chat_message_id",
            strategy = GenerationType.SEQUENCE
    )
    @SequenceGenerator(
            name = "sequence_chat_message_id",
            sequenceName = "sequence_chat_message_id"
    )
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "ticketstatus default 'OPEN'")
    private TicketStatus status;

    @Column(nullable = false)
    private Integer senderId;

    @Column(nullable = false)
    private Integer reportedId;

    private Integer moderatorId;

    @Column(nullable = false)
    private String message;

    private Integer roomId;

    @Column(nullable = false)
    private LocalDate submitDate;
}
