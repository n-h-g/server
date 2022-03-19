package com.cubs3d.messenger.controller;

import com.cubs3d.messenger.dto.ChatMessageDto;
import com.cubs3d.messenger.service.ChatMessageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("api/v1/messenger/chat/")
public class ChatMessageController {

    private final ChatMessageService chatMessageService;

    @PostMapping("/send_message")
    public void sendMessage(@RequestBody ChatMessageDto chatMessageDto) {
        chatMessageService.sendMessage(chatMessageDto);
    }
}
