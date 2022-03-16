package com.cubs3d.game.networking.message.incoming.clientpackets;

import com.cubs3d.game.dto.TokenDataResponse;
import com.cubs3d.game.dto.TokenResponse;
import com.cubs3d.game.networking.WebSocketClient;
import com.cubs3d.game.networking.message.incoming.ClientPacket;
import com.cubs3d.game.user.User;
import com.cubs3d.game.user.UserService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class Handshake extends ClientPacket {

    private final RestTemplate restTemplate;
    private final UserService userService;


    public Handshake() {
        restTemplate = this.getBean("restTemplate", RestTemplate.class);
        userService = this.getBean(UserService.class);
    }


    @Override
    public void handle() {
        try {
            WebSocketClient wsClient = (WebSocketClient) client;

            String jwtToken = body.getString("token");

            TokenDataResponse tokenData = this.getTokenData(jwtToken);

            if (tokenData == null) return;

            if (!this.tryLinkUserAndClient(tokenData.username(), wsClient)) {
                log.error("Error: Can't link user and client, user is null.");
                client.disconnect();
            }

        } catch (Exception e) {
            log.error("Error: "+ e);
        }
    }

    private TokenDataResponse getTokenData(String token) {
        return restTemplate.getForObject(
                "http://ACCOUNT/api/v1/account/token_valid/{token}",
                TokenDataResponse.class,
                token
        );
    }

    private boolean tryLinkUserAndClient(String username, WebSocketClient client) {
        User user = this.userService.getUserByUsername(username);

        if (user == null) return false;

        client.linkUser(user);
        return true;
    }
}
