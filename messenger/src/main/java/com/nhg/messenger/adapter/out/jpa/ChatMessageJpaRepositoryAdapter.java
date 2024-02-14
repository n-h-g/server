package com.nhg.messenger.adapter.out.jpa;

import com.nhg.messenger.adapter.out.jpa.entity.ChatMessageJpa;
import com.nhg.messenger.adapter.out.jpa.repository.ChatMessageJpaRepository;
import com.nhg.messenger.application.port.repository.ChatMessageRepository;
import com.nhg.messenger.domain.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChatMessageJpaRepositoryAdapter implements ChatMessageRepository {

    private final ChatMessageJpaRepository chatMessageJpaRepository;

    @Override
    public void save(ChatMessage chatMessage) {
        chatMessageJpaRepository.save(ChatMessageJpa.fromDomain(chatMessage));
    }
}
