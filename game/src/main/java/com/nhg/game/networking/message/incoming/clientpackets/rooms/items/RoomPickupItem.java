package com.nhg.game.networking.message.incoming.clientpackets.rooms.items;

import com.nhg.game.item.Item;
import com.nhg.game.item.ItemService;
import com.nhg.game.networking.WebSocketClient;
import com.nhg.game.networking.message.incoming.ClientPacket;
import com.nhg.game.networking.message.outgoing.OutgoingPacketHeaders;
import com.nhg.game.networking.message.outgoing.ServerPacket;
import com.nhg.game.room.RoomService;
import com.nhg.game.user.User;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public class RoomPickupItem extends ClientPacket {
    private final ItemService itemService;
    private final RoomService roomService;

    public RoomPickupItem() {
        itemService = this.getBean(ItemService.class);
        roomService = this.getBean(RoomService.class);
    }

    @Override
    public void handle() {
        try {
        WebSocketClient wsClient = (WebSocketClient) client;
        User user = wsClient.getUser();

        int itemId = body.getInt("id");

        Optional<Item> item = this.itemService.getItemById(itemId);

        if(item.isEmpty()) return;

        item.get().setRoom(null);

        roomService.pickupItem(item.get(), user.getEntity().getRoom());
        itemService.saveItem(item.get(), null);

        user.getEntity().getRoom().getUsers().sendBroadcastMessage(new ServerPacket(OutgoingPacketHeaders.RemoveItem, item.get()));

    } catch(Exception e) {
        log.error(e.getMessage());
    }
    }
}
