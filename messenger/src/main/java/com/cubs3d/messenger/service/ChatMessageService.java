package com.cubs3d.messenger.service;

import com.cubs3d.messenger.dto.ChatMessageRequest;
import com.cubs3d.messenger.dto.ChatMessageResponse;
import com.cubs3d.messenger.model.ChatMessage;
import com.cubs3d.messenger.repository.ChatMessageRepository;
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

        if (!existUserWithId(chatMessage.getSenderId())
                || (chatMessage.isRoomMessage() && !existRoomWithId(chatMessage.getDestinationId()))
                || (!chatMessage.isRoomMessage() && !existUserWithId(chatMessage.getDestinationId()))) {
            throw new IllegalArgumentException("Invalid ids");
        }

        String filteredText = filterText(chatMessageDto.text());

        chatMessageRepository.save(chatMessage);

        return new ChatMessageResponse(filteredText);
    }

    private String filterText(String text) {
        return text;
    }

    private Boolean existRoomWithId(int id) {
        return restTemplate.getForObject(
                "http://GAME/api/v1/game/room/exist_with_id/{id}",
                Boolean.class,
                id
        );
    }

    private Boolean existUserWithId(int id) {
        return restTemplate.getForObject(
                "http://GAME/api/v1/game/user/exist_with_id/{id}",
                Boolean.class,
                id
        );
    }
}
