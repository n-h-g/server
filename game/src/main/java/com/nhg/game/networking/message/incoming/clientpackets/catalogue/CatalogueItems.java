package com.nhg.game.networking.message.incoming.clientpackets.catalogue;

import com.nhg.game.dto.CatalogueItem;
import com.nhg.game.networking.WebSocketClient;
import com.nhg.game.networking.message.incoming.ClientPacket;
import com.nhg.game.networking.message.outgoing.OutgoingPacketHeaders;
import com.nhg.game.networking.message.outgoing.ServerPacket;
import com.nhg.game.user.User;
import com.nhg.game.utils.BeanRetriever;
import org.springframework.web.client.RestTemplate;

public class CatalogueItems extends ClientPacket {

    private final RestTemplate restTemplate;

    public CatalogueItems() {
        restTemplate = BeanRetriever.get("restTemplate", RestTemplate.class);
    }

    @Override
    public void handle() throws Exception {
        WebSocketClient wsClient = (WebSocketClient) client;
        User user = wsClient.getUser();

        if (user == null) return;

        int pageId = body.getInt("id");

        CatalogueItem[] items = restTemplate.getForEntity(
                "http://CATALOGUE/api/v1/catalogue/pages/{pageId}/items",
                CatalogueItem[].class,
                pageId
        ).getBody();

        if (items == null) return;

        client.sendMessage(new ServerPacket(OutgoingPacketHeaders.CatalogueItems, items));
    }
}
