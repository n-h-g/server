package com.nhg.game.networking.message.incoming.clientpackets.items;

import com.nhg.game.item.Item;
import com.nhg.game.item.ItemService;
import com.nhg.game.networking.WebSocketClient;
import com.nhg.game.networking.message.incoming.ClientPacket;
import com.nhg.game.networking.message.outgoing.OutgoingPacketHeaders;
import com.nhg.game.networking.message.outgoing.ServerPacket;
import com.nhg.game.utils.BeanRetriever;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class RequestLoadItems extends ClientPacket {

    private final ItemService itemService;

    public RequestLoadItems() {
        itemService = BeanRetriever.get(ItemService.class);
    }

    @Override
    public void handle() {
        try {
            WebSocketClient wsClient = (WebSocketClient) client;

            List<Item> items = itemService.getItemsByOwner(wsClient.getUser());

            //wsClient.sendMessage(new ServerPacket(OutgoingPacketHeaders.LoadItems, items));

        } catch(Exception e) {
            log.error("Error: " + e);
        }
    }
}
