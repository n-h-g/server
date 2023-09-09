package com.nhg.messenger.service;

import com.nhg.messenger.dto.ChatMessageRequest;
import com.nhg.messenger.dto.ChatMessageResponse;
import com.nhg.messenger.model.ChatMessage;
import com.nhg.messenger.repository.ChatMessageRepository;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
@AllArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final RestTemplate restTemplate;

    public ChatMessageResponse sendMessage(ChatMessageRequest chatMessageDto) {
        ChatMessage chatMessage = ChatMessage.builder()
                .senderId(chatMessageDto.senderId())
                .destinationId(chatMessageDto.destinationId())
                .text(chatMessageDto.text())
                .isRoomMessage(chatMessageDto.isRoomMessage())
                .build();

        if (!existsUserWithId(chatMessage.getSenderId())
                || (chatMessage.isRoomMessage() && !existsRoomWithId(chatMessage.getDestinationId()))
                || (!chatMessage.isRoomMessage() && !existsUserWithId(chatMessage.getDestinationId()))) {
            throw new IllegalArgumentException("Invalid ids");
        }

        String filteredText = filterText(chatMessageDto.text());

        chatMessageRepository.save(chatMessage);

        return new ChatMessageResponse(filteredText);
    }

    private String filterText(String text) {
        return text;
    }

    private Boolean existsRoomWithId(int id) {
        return restTemplate.getForObject(
                "http://GAME/api/v1/game/rooms/exists_with_id/{id}",
                Boolean.class,
                id
        );
    }

    private Boolean existsUserWithId(int id) {
        return restTemplate.getForObject(
                "http://GAME/api/v1/game/users/exists_with_id/{id}",
                Boolean.class,
                id
        );
    }
}
