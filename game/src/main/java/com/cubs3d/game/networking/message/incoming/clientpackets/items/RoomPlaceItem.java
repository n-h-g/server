package com.cubs3d.game.networking.message.incoming.clientpackets.items;

import com.cubs3d.game.item.Item;
import com.cubs3d.game.item.ItemService;
import com.cubs3d.game.networking.WebSocketClient;
import com.cubs3d.game.networking.message.incoming.ClientPacket;
import com.cubs3d.game.room.Room;
import com.cubs3d.game.room.RoomService;
import com.cubs3d.game.user.User;
import com.cubs3d.game.utils.Int2;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public class RoomPlaceItem extends ClientPacket {

    private final ItemService itemService;
    private final RoomService roomService;

    public RoomPlaceItem() {
        itemService = this.getBean(ItemService.class);
        roomService = this.getBean(RoomService.class);
    }

    @Override
    public void handle() {
        try {
            WebSocketClient wsClient = (WebSocketClient) client;
            User user = wsClient.getUser();

            int itemId = body.getInt("id");
            Int2 position = new Int2(body.getInt("x"), body.getInt("y"));

            Optional<Item> item = this.itemService.getItemById(itemId);

            if(item.isEmpty()) return;

            Item itemToAdd = itemService.saveItem(item.get(), user.getEntity().getRoom());
            roomService.placeItem(itemToAdd, user.getEntity().getRoom(), position);

        } catch(Exception e) {
            log.error(e.getMessage());
        }
    }
}
