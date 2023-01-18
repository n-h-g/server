package com.nhg.messenger.controller;

import com.nhg.messenger.service.FriendshipService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("api/v1/messenger/friendship")
public class FriendshipController {

    private final FriendshipService friendshipService;

    @PostMapping("/from/{fromId}/to/{toId}")
    public boolean sendFriendship(@PathVariable("fromId") int fromId, @PathVariable("toId") int toId) {
        return friendshipService.requestFriendship(fromId, toId);
    }

}
