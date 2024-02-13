package com.nhg.messenger.adapter.out.rest;

import com.nhg.messenger.application.port.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class RoomRepositoryRestAdapter implements RoomRepository {

    private final RestTemplate restTemplate;


    @Override
    public boolean existsById(int roomId) {
        try {
            restTemplate.headForHeaders(
                    "http://GAME/api/v1/game/rooms/{id}",
                    roomId
            );
        } catch (RestClientException ignored) {
            return false;
        }

        return true;
    }
}
