package com.nhg.messenger.controller;

import com.nhg.messenger.dto.*;
import com.nhg.messenger.service.FriendshipService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("api/v1/messenger/friendship")
public class FriendshipController {

    private final FriendshipService friendshipService;

}
