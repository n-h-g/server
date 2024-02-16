package com.nhg.game.adapter.in.websocket.packet.user.item;

import com.nhg.game.adapter.in.websocket.ClientUserMap;
import com.nhg.game.adapter.in.websocket.IncomingPacket;
import com.nhg.game.adapter.in.websocket.mapper.ItemToJsonMapper;
import com.nhg.game.adapter.out.websocket.OutPacketHeaders;
import com.nhg.game.adapter.out.websocket.OutgoingPacket;
import com.nhg.game.application.usecase.item.GetInventoryItemsUseCase;
import com.nhg.game.domain.item.Item;
import com.nhg.game.domain.user.User;
import com.nhg.game.infrastructure.networking.Client;
import com.nhg.game.infrastructure.networking.packet.ClientPacket;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;

import java.util.List;

@RequiredArgsConstructor
@IncomingPacket(header = 14)
public class InventoryItems implements ClientPacket<JSONObject> {

    private final ClientUserMap clientUserMap;
    private final GetInventoryItemsUseCase inventoryItemsUseCase;
    private final ItemToJsonMapper itemMapper;

    @Override
    public void handle(Client<?> client, JSONObject body) {
        User user = clientUserMap.getUser(client.getId());

        if (user == null) return;

        List<Item> items = inventoryItemsUseCase.byOwner(user);

        OutgoingPacket.send(
                client,
                OutPacketHeaders.InventoryItems,
                itemMapper.itemsToJson(items)
        );
    }
}
