package com.cubs3d.messenger.service;

import com.cubs3d.messenger.dto.ChatMessageDto;
import com.cubs3d.messenger.model.ChatMessage;
import com.cubs3d.messenger.repository.ChatMessageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;

    public void sendMessage(ChatMessageDto chatMessageDto) {
        ChatMessage chatMessage = ChatMessage.builder()
                .senderId(chatMessageDto.senderId())
                .destinationId(chatMessageDto.destinationId())
                .text(chatMessageDto.text())
                .isRoomMessage(chatMessageDto.isRoomMessage())
                .build();

        chatMessageRepository.save(chatMessage);
    }
}
