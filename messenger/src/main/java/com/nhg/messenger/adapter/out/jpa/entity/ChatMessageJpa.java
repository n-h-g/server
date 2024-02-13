package com.nhg.messenger.adapter.out.jpa.entity;

import com.nhg.messenger.domain.ChatMessage;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageJpa {

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
    private Integer senderId;

    @Column(nullable = false)
    private Integer recipientId;

    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private boolean isRoomMessage;


    public static ChatMessageJpa fromDomain(ChatMessage chatMessage) {
        return ChatMessageJpa.builder()
                .id(chatMessage.getId())
                .senderId(chatMessage.getSenderId())
                .recipientId(chatMessage.getRecipientId())
                .isRoomMessage(chatMessage.isRoomMessage())
                .build();
    }
}
