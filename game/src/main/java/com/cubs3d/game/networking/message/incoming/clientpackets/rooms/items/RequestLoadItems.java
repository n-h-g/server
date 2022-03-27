package com.cubs3d.game.networking.message.incoming.clientpackets.rooms.items;

import com.cubs3d.game.item.Item;
import com.cubs3d.game.item.ItemService;
import com.cubs3d.game.networking.WebSocketClient;
import com.cubs3d.game.networking.message.incoming.ClientPacket;
import com.cubs3d.game.networking.message.outgoing.OutgoingPacketHeaders;
import com.cubs3d.game.networking.message.outgoing.ServerPacket;
import com.cubs3d.game.room.RoomService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class RequestLoadItems extends ClientPacket {

    private final ItemService itemService;

    public RequestLoadItems() {
        itemService = this.getBean(ItemService.class);
    }

    @Override
    public void handle() {
        try {
            WebSocketClient wsClient = (WebSocketClient) client;

            List<Item> items = itemService.getItemsByOwner(wsClient.getUser());

            wsClient.sendMessage(new ServerPacket(OutgoingPacketHeaders.LoadItems, items));

        } catch(Exception e) {
            log.error("Error: " + e);
        }
    }
}
