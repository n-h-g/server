package com.nhg.game.networking.message.incoming.clientpackets.rooms.items;

import com.nhg.game.item.Item;
import com.nhg.game.item.ItemService;
import com.nhg.game.networking.WebSocketClient;
import com.nhg.game.networking.message.incoming.ClientPacket;
import com.nhg.game.networking.message.outgoing.OutgoingPacketHeaders;
import com.nhg.game.networking.message.outgoing.ServerPacket;
import com.nhg.game.room.Room;
import com.nhg.game.room.RoomService;
import com.nhg.game.user.User;
import com.nhg.game.utils.BeanRetriever;

public class PlaceItem extends ClientPacket {

    private RoomService roomService;
    private ItemService itemService;

    public PlaceItem() {
        roomService = BeanRetriever.get(RoomService.class);
        itemService = BeanRetriever.get(ItemService.class);
    }

    @Override
    public void handle() throws Exception {
        WebSocketClient wsClient = (WebSocketClient) client;
        User user = wsClient.getUser();

        if (user == null) return;

        Room room = user.getEntity().getRoom();

        if (room == null) return;

        int itemId = body.getInt("id");

        Item item = itemService.getItemByIdAndOwner(user, itemId);

        if (item == null) return;

        roomService.placeItem(item, room);
        itemService.userPlaceItem(user, item, room);

        room.getUsers().sendBroadcastMessage(
                new ServerPacket(OutgoingPacketHeaders.AddRoomEntity, item.getEntity()));
    }
}
