package com.nhg.messenger.adapter.in.rest;

import com.nhg.messenger.application.dto.ChatMessageRequest;
import com.nhg.messenger.application.dto.ChatMessageResponse;
import com.nhg.messenger.application.usecase.ChatMessageUseCase;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@AllArgsConstructor
@RestController
@RequestMapping("api/v1/messenger/chat")
public class ChatMessageController {

    private final ChatMessageUseCase chatMessageUseCase;

    @PostMapping
    public ChatMessageResponse sendMessage(@RequestBody ChatMessageRequest ChatMessageRequest) {
        return chatMessageUseCase.sendMessage(ChatMessageRequest);
    }
}