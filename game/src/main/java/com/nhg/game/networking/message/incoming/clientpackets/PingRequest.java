package com.nhg.game.networking.message.incoming.clientpackets;

import com.nhg.game.dto.TokenDataResponse;
import com.nhg.game.networking.WebSocketClient;
import com.nhg.game.networking.message.incoming.ClientPacket;
import com.nhg.game.networking.message.outgoing.OutgoingPacketHeaders;
import com.nhg.game.networking.message.outgoing.ServerPacket;
import com.nhg.game.user.User;
import com.nhg.game.user.UserService;
import com.nhg.game.utils.BeanRetriever;
import org.springframework.web.client.RestTemplate;

public class PingRequest extends ClientPacket {

    private final RestTemplate restTemplate;
    private final UserService userService;


    public PingRequest() {
        restTemplate = BeanRetriever.get("restTemplate", RestTemplate.class);
        userService = BeanRetriever.get(UserService.class);
    }


    @Override
    public void handle() {
        WebSocketClient wsClient = (WebSocketClient) client;

        boolean doLogin = wsClient.getUser() == null;

        client.sendMessage(new ServerPacket(OutgoingPacketHeaders.PongResponse, doLogin));
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
