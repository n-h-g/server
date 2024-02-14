package com.nhg.messenger.application.usecase;

import com.nhg.common.domain.UseCase;
import com.nhg.messenger.application.dto.ChatMessageRequest;
import com.nhg.messenger.application.dto.ChatMessageResponse;
import com.nhg.messenger.application.port.WordFilter;
import com.nhg.messenger.application.port.repository.ChatMessageRepository;
import com.nhg.messenger.application.port.repository.RoomRepository;
import com.nhg.messenger.application.port.repository.UserRepository;
import com.nhg.messenger.domain.ChatMessage;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class ChatMessageUseCase {

    private final WordFilter wordFilter;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final ChatMessageRepository chatMessageRepository;

    public ChatMessageResponse sendMessage(@NonNull ChatMessageRequest chatMessageRequest) {
        ChatMessage chatMessage = ChatMessage.builder()
                .senderId(chatMessageRequest.senderId())
                .recipientId(chatMessageRequest.recipientId())
                .text(chatMessageRequest.text())
                .isRoomMessage(chatMessageRequest.isRoomMessage())
                .build();

        if (!userRepository.existsById(chatMessage.getSenderId())
                || (chatMessage.isRoomMessage() && !roomRepository.existsById(chatMessage.getRecipientId()))
                || (!chatMessage.isRoomMessage() && !userRepository.existsById(chatMessage.getRecipientId()))) {
            throw new IllegalArgumentException("Invalid ids");
        }

        chatMessageRepository.save(chatMessage);

        String filteredText = wordFilter.filterText(chatMessageRequest.text());

        return new ChatMessageResponse(filteredText);
    }
}
