package com.nhg.game.networking.message.incoming.clientpackets.catalogue;

import com.nhg.game.dto.CataloguePage;
import com.nhg.game.networking.WebSocketClient;
import com.nhg.game.networking.message.incoming.ClientPacket;
import com.nhg.game.networking.message.outgoing.OutgoingPacketHeaders;
import com.nhg.game.networking.message.outgoing.ServerPacket;
import com.nhg.game.user.User;
import com.nhg.game.utils.BeanRetriever;
import org.springframework.web.client.RestTemplate;

public class CataloguePages extends ClientPacket {

    private final RestTemplate restTemplate;

    public CataloguePages() {
        restTemplate = BeanRetriever.get("restTemplate", RestTemplate.class);
    }

    @Override
    public void handle() {
        WebSocketClient wsClient = (WebSocketClient) client;
        User user = wsClient.getUser();

        if (user == null) return;

        CataloguePage[] pages = restTemplate.getForEntity(
                "http://CATALOGUE/api/v1/catalogue/pages",
                CataloguePage[].class
        ).getBody();

        if (pages == null) return;

        client.sendMessage(new ServerPacket(OutgoingPacketHeaders.CataloguePages, pages));
    }
}
