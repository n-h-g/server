package com.nhg.messenger.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ChatMessage {

    private Integer id;
    private Integer senderId;
    private Integer recipientId;
    private String text;
    private boolean isRoomMessage;
}
