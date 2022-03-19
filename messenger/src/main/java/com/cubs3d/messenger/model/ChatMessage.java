package com.cubs3d.messenger.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {

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

    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private Integer senderId;

    @Column(nullable = false)
    private Integer destinationId;

    @Column(nullable = false)
    private boolean isRoomMessage;
}
