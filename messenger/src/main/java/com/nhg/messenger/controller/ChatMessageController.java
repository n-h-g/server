package com.nhg.messenger.controller;

import com.nhg.messenger.dto.ChatMessageRequest;
import com.nhg.messenger.dto.ChatMessageResponse;
import com.nhg.messenger.service.ChatMessageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("api/v1/messenger/chat")
public class ChatMessageController {

    private final ChatMessageService chatMessageService;

    @PostMapping
    public ChatMessageResponse sendMessage(ChatMessageRequest ChatMessageRequest) {
        return chatMessageService.sendMessage(ChatMessageRequest);
    }
}
