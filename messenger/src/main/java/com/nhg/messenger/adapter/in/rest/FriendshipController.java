package com.nhg.messenger.adapter.in.rest;

import com.nhg.messenger.application.usecase.SendFriendshipUseCase;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/messenger/friendship")
public class FriendshipController {

    private final SendFriendshipUseCase sendFriendshipUseCase;

    @PostMapping("/from/{fromId}/to/{toId}")
    public boolean sendFriendship(@PathVariable("fromId") int fromId, @PathVariable("toId") int toId) {
        return sendFriendshipUseCase.sendFriendship(fromId, toId);
    }

}