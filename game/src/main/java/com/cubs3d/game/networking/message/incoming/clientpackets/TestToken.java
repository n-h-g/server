package com.cubs3d.game.networking.message.incoming.clientpackets;

import com.cubs3d.game.dto.TokenResponse;
import com.cubs3d.game.networking.message.incoming.ClientPacket;
import com.cubs3d.game.networking.message.outgoing.serverpackets.TestTokenResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class TestToken extends ClientPacket {

    private final RestTemplate restTemplate;

    public TestToken() {
        restTemplate = this.getBean("restTemplate", RestTemplate.class);
    }

    @Override
    public void handle() {
        try {
            String username = body.getString("username");

            TokenResponse token = restTemplate.getForObject(
                    "http://ACCOUNT/api/v1/account/generate_token/{username}",
                    TokenResponse.class,
                    username
            );

            if (token == null) return;

            client.SendMessage(new TestTokenResponse(token.token()));

        } catch (Exception e) {
            log.error("Error: " + e);
        }
    }
}