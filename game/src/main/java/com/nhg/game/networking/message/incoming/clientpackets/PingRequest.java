package com.nhg.game.networking.message.incoming.clientpackets;

import com.nhg.game.dto.TokenDataResponse;
import com.nhg.game.networking.WebSocketClient;
import com.nhg.game.networking.message.incoming.ClientPacket;
import com.nhg.game.networking.message.outgoing.OutgoingPacketHeaders;
import com.nhg.game.networking.message.outgoing.ServerPacket;
import com.nhg.game.user.User;
import com.nhg.game.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class PingRequest extends ClientPacket {

    private final RestTemplate restTemplate;
    private final UserService userService;


    public PingRequest() {
        restTemplate = this.getBean("restTemplate", RestTemplate.class);
        userService = this.getBean(UserService.class);
    }


    @Override
    public void handle() {
        try {
            WebSocketClient wsClient = (WebSocketClient) client;

            boolean doLogin = wsClient.getUser() == null;

            client.sendMessage(new ServerPacket(OutgoingPacketHeaders.PongResponse, doLogin));

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