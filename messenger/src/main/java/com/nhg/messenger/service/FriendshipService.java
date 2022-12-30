package com.nhg.messenger.service;

import com.nhg.messenger.repository.FriendshipRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class FriendshipService {
    private final FriendshipRepository friendshipRepository;
    private final RestTemplate restTemplate;


}
