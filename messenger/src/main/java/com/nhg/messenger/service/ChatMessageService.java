package com.nhg.messenger.service;

import com.nhg.messenger.dto.ChatMessageRequest;
import com.nhg.messenger.dto.ChatMessageResponse;
import com.nhg.messenger.dto.WordFilterResponse;
import com.nhg.messenger.model.ChatMessage;
import com.nhg.messenger.repository.ChatMessageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;


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

        chatMessageRepository.save(chatMessage);

        String filteredText = filterText(chatMessageDto.text());

        return new ChatMessageResponse(filteredText);
    }

    private String filterText(String text) {
        WordFilterResponse[] wordFilters = restTemplate.getForObject(
                "http://MODERATION/api/v1/moderation/word_filters",
                WordFilterResponse[].class
        );
        if (wordFilters != null && Arrays.stream(wordFilters).map(WordFilterResponse::word).toList().contains(text)) {
            String finalText = text;
            WordFilterResponse filtered = Arrays.stream(wordFilters).filter(wf -> wf.word().equalsIgnoreCase(finalText)).findFirst().get();
            if (filtered.hideMessage()) {
                return "";
            } else {
                text = filtered.replacement();
            }
        }
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
