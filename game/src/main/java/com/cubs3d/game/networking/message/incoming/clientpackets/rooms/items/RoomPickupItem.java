package com.cubs3d.game.networking.message.incoming.clientpackets.rooms.items;

import com.cubs3d.game.item.Item;
import com.cubs3d.game.item.ItemService;
import com.cubs3d.game.networking.WebSocketClient;
import com.cubs3d.game.networking.message.incoming.ClientPacket;
import com.cubs3d.game.networking.message.outgoing.OutgoingPacketHeaders;
import com.cubs3d.game.networking.message.outgoing.ServerPacket;
import com.cubs3d.game.room.RoomService;
import com.cubs3d.game.user.User;
import com.cubs3d.game.utils.Int2;
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
