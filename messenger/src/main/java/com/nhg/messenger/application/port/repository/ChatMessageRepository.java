package com.nhg.messenger.application.port.repository;

import com.nhg.messenger.domain.ChatMessage;

public interface ChatMessageRepository {

    void save(ChatMessage chatMessage);
}
