package com.nhg.messenger.adapter.out.rest;

import com.nhg.messenger.application.port.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class UserRepositoryRestAdapter implements UserRepository {

    private final RestTemplate restTemplate;

    @Override
    public boolean existsById(int userId) {
        try {
            restTemplate.headForHeaders(
                    "http://GAME/api/v1/game/users/{id}",
                    userId
            );
        } catch (RestClientException ignored) {
            return false;
        }

        return true;
    }
}
