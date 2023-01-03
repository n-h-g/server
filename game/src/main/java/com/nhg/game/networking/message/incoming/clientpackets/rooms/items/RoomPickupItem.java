package com.nhg.game.networking.message.incoming.clientpackets.rooms.items;

import com.nhg.game.item.Item;
import com.nhg.game.item.ItemService;
import com.nhg.game.networking.WebSocketClient;
import com.nhg.game.networking.message.incoming.ClientPacket;
import com.nhg.game.networking.message.outgoing.OutgoingPacketHeaders;
import com.nhg.game.networking.message.outgoing.ServerPacket;
import com.nhg.game.room.RoomService;
import com.nhg.game.user.User;
import com.nhg.game.utils.BeanRetriever;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public class RoomPickupItem extends ClientPacket {
    private final ItemService itemService;
    private final RoomService roomService;

    public RoomPickupItem() {
        itemService = BeanRetriever.get(ItemService.class);
        roomService = BeanRetriever.get(RoomService.class);
    }

    @Override
    public void handle() {
        try {
        WebSocketClient wsClient = (WebSocketClient) client;
        User user = wsClient.getUser();

        int itemId = body.getInt("id");

        Item item = user.getEntity().getRoom().getItem(itemId);

        if(item == null) return;

        item.setRoom(null);

        roomService.pickupItem(item, user.getEntity().getRoom());
        itemService.saveItem(item, null);

        user.getEntity().getRoom().getUsers().sendBroadcastMessage(new ServerPacket(OutgoingPacketHeaders.RemoveItem, item));

    } catch(Exception e) {
        log.error(e.getMessage());
    }
    }
}
