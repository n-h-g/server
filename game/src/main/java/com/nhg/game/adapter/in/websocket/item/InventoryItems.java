package com.nhg.game.adapter.in.websocket.item;

import com.nhg.game.adapter.in.websocket.ClientUserMap;
import com.nhg.game.adapter.in.websocket.IncomingPacket;
import com.nhg.game.adapter.out.websocket.OutPacketHeaders;
import com.nhg.game.adapter.out.websocket.OutgoingPacket;
import com.nhg.game.application.usecase.item.GetInventoryItemsUseCase;
import com.nhg.game.domain.item.Item;
import com.nhg.game.domain.user.User;
import com.nhg.game.infrastructure.context.BeanRetriever;
import com.nhg.game.infrastructure.mapper.ItemToJsonMapper;

import java.util.List;

public class InventoryItems extends IncomingPacket {

    private final ClientUserMap clientUserMap;
    private final GetInventoryItemsUseCase inventoryItemsUseCase;
    private final ItemToJsonMapper itemMapper;

    public InventoryItems() {
        clientUserMap = BeanRetriever.get(ClientUserMap.class);
        inventoryItemsUseCase = BeanRetriever.get(GetInventoryItemsUseCase.class);
        itemMapper = BeanRetriever.get(ItemToJsonMapper.class);
    }

    @Override
    public void handle() throws Exception {
        User user = clientUserMap.getUser(client.getId());

        if (user == null) return;

        List<Item> items = inventoryItemsUseCase.byOwner(user);

        client.sendMessage(new OutgoingPacket(
                OutPacketHeaders.InventoryItems,
                itemMapper.itemsToJson(items)
        ));
    }
}
