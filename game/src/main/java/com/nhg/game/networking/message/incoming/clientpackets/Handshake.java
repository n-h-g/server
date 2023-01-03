package com.nhg.game.networking.message.incoming.clientpackets;

import com.nhg.game.dto.TokenDataResponse;
import com.nhg.game.networking.WebSocketClient;
import com.nhg.game.networking.message.incoming.ClientPacket;
import com.nhg.game.networking.message.outgoing.OutgoingPacketHeaders;
import com.nhg.game.networking.message.outgoing.ServerPacket;
import com.nhg.game.user.User;
import com.nhg.game.user.UserService;
import com.nhg.game.utils.BeanRetriever;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class Handshake extends ClientPacket {

    private final RestTemplate restTemplate;
    private final UserService userService;


    public Handshake() {
        restTemplate = BeanRetriever.get("restTemplate", RestTemplate.class);
        userService = BeanRetriever.get(UserService.class);
    }


    @Override
    public void handle() {
        try {
            WebSocketClient wsClient = (WebSocketClient) client;

            String id = body.getString("sso");

            /*TokenDataResponse tokenData = this.getTokenData(jwtToken);

            if (tokenData == null) return;

            log.error(tokenData.username());*/

            User user = userService.getUserById(Integer.parseInt(id));

            if (!this.tryLinkUserAndClient(user, wsClient)) {
                log.error("Error: Can't link user and client, user is null.");
                client.disconnect();
                return;
            }

            client.sendMessage(new ServerPacket(OutgoingPacketHeaders.LoginMessageCheck, true));


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
