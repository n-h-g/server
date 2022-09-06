package com.nhg.game.gateway;

import com.nhg.game.dto.TokenResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@AllArgsConstructor
@RestController
public class Gateway {

    private final RestTemplate restTemplate;

    @GetMapping("/api/v1/account/generate_token/{username}")
    public TokenResponse generateToken(@PathVariable String username) {

        return restTemplate.getForObject(
                "http://ACCOUNT/api/v1/account/generate_token/{username}",
                TokenResponse.class,
                username
        );
    }

}
