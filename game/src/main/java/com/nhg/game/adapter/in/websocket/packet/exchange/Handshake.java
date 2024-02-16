package com.nhg.game.adapter.in.websocket.packet.exchange;

import com.nhg.game.adapter.in.InPacketHeader;
import com.nhg.game.adapter.in.websocket.ClientUserMap;
import com.nhg.game.adapter.in.websocket.IncomingPacket;
import com.nhg.game.adapter.out.websocket.OutPacketHeader;
import com.nhg.game.adapter.out.websocket.OutgoingPacket;
import com.nhg.game.application.usecase.user.FindUserUseCase;
import com.nhg.game.domain.user.User;
import com.nhg.game.infrastructure.networking.Client;
import com.nhg.game.infrastructure.networking.packet.ClientPacket;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@IncomingPacket(header = InPacketHeader.Handshake)
public class Handshake implements ClientPacket<JSONObject> {

    private final RestTemplate restTemplate;
    private final FindUserUseCase findUser;
    private final ClientUserMap clientUserMap;


    @Override
    public void handle(Client<?> client, JSONObject body) {
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

        clientUserMap.put(client.getId(), optUser.get());

        OutgoingPacket.send(
                client,
                OutPacketHeader.LoginMessageCheck,
                true
        );
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
