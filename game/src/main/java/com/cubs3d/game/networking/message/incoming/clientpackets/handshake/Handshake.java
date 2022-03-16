package com.cubs3d.game.networking.message.incoming.clientpackets.handshake;

import com.cubs3d.game.dto.TokenDataResponse;
import com.cubs3d.game.dto.TokenResponse;
import com.cubs3d.game.networking.WebSocketClient;
import com.cubs3d.game.networking.message.incoming.ClientPacket;
import com.cubs3d.game.networking.message.outgoing.serverpackets.handshake.LoginMessageCheck;
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

            String jwtToken = body.getString("sso");

            TokenDataResponse tokenData = this.getTokenData(jwtToken);

            if (tokenData == null) return;

            log.error(tokenData.username());

            User user = userService.getUserByUsername(tokenData.username());

            if (!this.tryLinkUserAndClient(user, wsClient)) {
                log.error("Error: Can't link user and client, user is null.");
                client.disconnect();
                return;
            }

            client.SendMessage(new LoginMessageCheck(true));


        } catch (Exception e) {
            log.error("Error: "+ e);
        }
    }

    private TokenDataResponse getTokenData(String token) {
        return restTemplate.getForObject(
                "http://ACCOUNT/api/v1/account/token_data/{token}",
                TokenDataResponse.class,
                token
        );
    }

    private boolean tryLinkUserAndClient(User user, WebSocketClient client) {
        if (user == null) return false;

        client.linkUser(user);
        return true;
    }
}
