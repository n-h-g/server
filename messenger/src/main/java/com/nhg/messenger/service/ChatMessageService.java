package com.nhg.messenger.service;

import com.nhg.messenger.dto.ChatMessageRequest;
import com.nhg.messenger.dto.ChatMessageResponse;
import com.nhg.messenger.model.Friendship;
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
    private final FriendshipService friendshipService;

    public ChatMessageResponse sendMessage(ChatMessageRequest chatMessageDto) {

        Friendship friendship = this.friendshipService.getFriendshipBySenderOrDestination(chatMessageDto.senderId(), chatMessageDto.destinationId());

        if(!chatMessageDto.isRoomMessage() && friendship == null) {
            return new ChatMessageResponse("", -1);
        }

        ChatMessage chatMessage = ChatMessage.builder()
                .senderId(chatMessageDto.senderId())
                .destinationId(chatMessageDto.destinationId())
                .friendship(friendship)
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

        return new ChatMessageResponse(filteredText, chatMessage.getId());
    }

    private String filterText(String text) {
        return text;
    }

    private Boolean existsRoomWithId(int id) {
        return restTemplate.getForObject(
                "http://GAME/api/v1/game/room/exists_with_id/{id}",
                Boolean.class,
                id
        );
    }

    private Boolean existsUserWithId(int id) {
        return restTemplate.getForObject(
                "http://GAME/api/v1/game/user/exists_with_id/{id}",
                Boolean.class,
                id
        );
    }
}
