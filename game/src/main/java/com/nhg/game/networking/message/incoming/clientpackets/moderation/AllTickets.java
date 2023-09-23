package com.nhg.game.networking.message.incoming.clientpackets.moderation;

import com.nhg.game.dto.TicketResponse;
import com.nhg.game.networking.WebSocketClient;
import com.nhg.game.networking.message.incoming.ClientPacket;
import com.nhg.game.utils.BeanRetriever;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class AllTickets extends ClientPacket {

    private final RestTemplate restTemplate;

    public AllTickets() {
        restTemplate = BeanRetriever.get("restTemplate", RestTemplate.class);
    }

    @Override
    public void handle() throws Exception {
        WebSocketClient wsClient = (WebSocketClient) client;
        Integer senderId = body.getInt("senderId");

        if (senderId == null)
            return;

        TicketResponse[] tickets = restTemplate.getForObject(
                "http://MODERATION/api/v1/moderation/tickets/{senderId}",
                TicketResponse[].class,
                senderId);

    }
}
