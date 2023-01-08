package com.nhg.game.networking.message.incoming.clientpackets.users;

import com.nhg.game.item.Item;
import com.nhg.game.item.ItemService;
import com.nhg.game.networking.WebSocketClient;
import com.nhg.game.networking.message.incoming.ClientPacket;
import com.nhg.game.networking.message.outgoing.OutgoingPacketHeaders;
import com.nhg.game.networking.message.outgoing.ServerPacket;
import com.nhg.game.user.User;
import com.nhg.game.utils.BeanRetriever;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class InventoryItems extends ClientPacket {

    private ItemService itemService;

    public InventoryItems() {
        itemService = BeanRetriever.get(ItemService.class);
    }

    @Override
    public void handle() {
        try {
            WebSocketClient wsClient = (WebSocketClient) client;
            User user = wsClient.getUser();

            if (user == null) return;

            List<Item> items = itemService.getItemsByOwner(user);

            client.sendMessage(new ServerPacket(
                    OutgoingPacketHeaders.InventoryItems,
                    items
            ));

        } catch(Exception e) {
            log.error("Error: "+ e);
            e.printStackTrace();
        }
    }
}
