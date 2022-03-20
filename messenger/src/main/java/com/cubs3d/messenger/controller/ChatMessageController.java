package com.cubs3d.messenger.controller;

import com.cubs3d.messenger.dto.ChatMessageRequest;
import com.cubs3d.messenger.dto.ChatMessageResponse;
import com.cubs3d.messenger.service.ChatMessageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("api/v1/messenger/chat")
public class ChatMessageController {

    private final ChatMessageService chatMessageService;

    @GetMapping("/send_message/{senderId}/{destinationId}/{text}/{isRoomMessage}")
    public ChatMessageResponse sendMessage(ChatMessageRequest ChatMessageRequest) {
        return chatMessageService.sendMessage(ChatMessageRequest);
    }
}
