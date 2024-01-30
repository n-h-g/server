package com.nhg.game.adapter.in.websocket;

import com.nhg.game.application.usecase.user.FindUserUseCase;
import com.nhg.game.domain.user.User;
import com.nhg.game.infrastructure.context.BeanRetriever;
import com.nhg.game.infrastructure.networking.ClientUserMap;
import com.nhg.game.infrastructure.networking.OutgoingPacket;
import com.nhg.game.adapter.out.websocket.OutPacketHeaders;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Slf4j
public class Handshake extends IncomingPacket {

    private final RestTemplate restTemplate;
    private final FindUserUseCase findUser;
    private final ClientUserMap<String> clientUserMap;


    @SuppressWarnings("unchecked")
    public Handshake() {
        restTemplate = BeanRetriever.get("restTemplate", RestTemplate.class);
        findUser = BeanRetriever.get(FindUserUseCase.class);
        clientUserMap = BeanRetriever.get(ClientUserMap.class);
    }


    @Override
    public void handle() throws Exception {
        String id = body.getString("sso");

        // TODO get the user from jwt
        int tempId = Integer.parseInt(id);
        /*
        TokenDataResponse tokenData = this.getTokenData(jwtToken);

        if (tokenData == null) return;

        log.error(tokenData.username());
        */

        Optional<User> optUser = findUser.byId(tempId);

        if (optUser.isEmpty()) {
            log.error("Error: Can't link user and client, user is null.");
            client.disconnect();
            return;
        }

        clientUserMap.put((String) client.getId(), optUser.get());

        client.sendMessage(new OutgoingPacket(OutPacketHeaders.LoginMessageCheck, true));
    }

    /*
    private TokenDataResponse getTokenData(String token) {
        return restTemplate.getForObject(
                "http://ACCOUNT/api/v1/accounts/token_data/{token}",
                TokenDataResponse.class,
                token
        );
    }
    */
}
