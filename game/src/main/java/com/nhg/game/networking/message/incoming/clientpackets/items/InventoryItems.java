package com.nhg.game.networking.message.incoming.clientpackets.items;

import com.nhg.game.item.Item;
import com.nhg.game.item.ItemService;
import com.nhg.game.networking.WebSocketClient;
import com.nhg.game.networking.message.incoming.ClientPacket;
import com.nhg.game.networking.message.outgoing.OutgoingPacketHeaders;
import com.nhg.game.networking.message.outgoing.ServerPacket;
import com.nhg.game.user.User;
import com.nhg.game.utils.BeanRetriever;

import java.util.List;

public class InventoryItems extends ClientPacket {

    private final ItemService itemService;

    public InventoryItems() {
        itemService = BeanRetriever.get(ItemService.class);
    }

    @Override
    public void handle() throws Exception {
        WebSocketClient wsClient = (WebSocketClient) client;
        User user = wsClient.getUser();

        if (user == null) return;

        List<Item> items = itemService.getItemsByOwner(user);

        client.sendMessage(new ServerPacket(
                OutgoingPacketHeaders.InventoryItems,
                items
        ));
    }
}
