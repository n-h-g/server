package com.nhg.game.networking.message.incoming.clientpackets.rooms.items;

import com.nhg.game.item.Item;
import com.nhg.game.item.ItemService;
import com.nhg.game.networking.WebSocketClient;
import com.nhg.game.networking.message.incoming.ClientPacket;
import com.nhg.game.networking.message.outgoing.OutgoingPacketHeaders;
import com.nhg.game.networking.message.outgoing.ServerPacket;
import com.nhg.game.room.RoomService;
import com.nhg.game.user.User;
import com.nhg.game.utils.BubbleAlertType;
import com.nhg.game.utils.Int3;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;

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
            Int3 position = new Int3(body.getInt("x"), body.getInt("y"), body.getInt("z"));

            Optional<Item> item = this.itemService.getItemById(itemId);

            if(item.isEmpty()) return;

            item.get().setRoom(user.getEntity().getRoom());


            if(!roomService.placeItem(item.get(), user.getEntity().getRoom(), position)) {
                user.getClient().sendMessage(new ServerPacket(OutgoingPacketHeaders.BubbleAlert,
                        new JSONObject()
                                .put("message", "Impossibile posizionare il furno.")
                                .put("action", BubbleAlertType.Default)
                                .put("goalId", -1)
                ));
            } else {
                Item itemToAdd = itemService.saveItem(item.get(), user.getEntity().getRoom());
                user.getEntity().getRoom().getUsers().sendBroadcastMessage(new ServerPacket(OutgoingPacketHeaders.AddItem, itemToAdd));
            }


        } catch(Exception e) {
            log.error(e.getMessage());
        }
    }
}
