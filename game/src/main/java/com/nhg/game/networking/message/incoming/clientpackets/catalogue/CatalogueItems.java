package com.nhg.game.networking.message.incoming.clientpackets.catalogue;

import com.nhg.game.dto.CatalogueItem;
import com.nhg.game.networking.WebSocketClient;
import com.nhg.game.networking.message.incoming.ClientPacket;
import com.nhg.game.networking.message.outgoing.OutgoingPacketHeaders;
import com.nhg.game.networking.message.outgoing.ServerPacket;
import com.nhg.game.user.User;
import com.nhg.game.utils.BeanRetriever;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class CatalogueItems extends ClientPacket {

    private final RestTemplate restTemplate;

    public CatalogueItems() {
        restTemplate = BeanRetriever.get("restTemplate", RestTemplate.class);
    }

    @Override
    public void handle() {
        try {
            WebSocketClient wsClient = (WebSocketClient) client;
            User user = wsClient.getUser();

            if (user == null) return;

            int pageId = body.getInt("id");

            CatalogueItem[] items = restTemplate.getForEntity(
                    "http://CATALOGUE/api/v1/catalogue/page/{pageId}",
                    CatalogueItem[].class,
                    pageId
            ).getBody();

            if (items == null) return;

            client.sendMessage(new ServerPacket(OutgoingPacketHeaders.CatalogueItems, items));

        } catch(Exception e) {
            log.error("Error: "+ e);
            e.printStackTrace();
        }
    }
}
