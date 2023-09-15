package com.nhg.game.networking.message.incoming.clientpackets.rooms.items;

import com.nhg.game.item.Item;
import com.nhg.game.networking.WebSocketClient;
import com.nhg.game.networking.message.incoming.ClientPacket;
import com.nhg.game.networking.message.outgoing.OutgoingPacketHeaders;
import com.nhg.game.networking.message.outgoing.ServerPacket;
import com.nhg.game.room.Room;
import com.nhg.game.room.RoomService;
import com.nhg.game.room.entity.Entity;
import com.nhg.game.room.entity.component.ComponentType;
import com.nhg.game.room.entity.component.InteractionComponent;
import com.nhg.game.room.entity.component.ItemComponent;
import com.nhg.game.user.User;
import com.nhg.game.utils.BeanRetriever;

import java.util.UUID;

public class PickupItem extends ClientPacket {

    private RoomService roomService;

    public PickupItem() {
        roomService = BeanRetriever.get(RoomService.class);
    }

    @Override
    public void handle() throws Exception {
        WebSocketClient wsClient = (WebSocketClient) client;
        User user = wsClient.getUser();

        if (user == null) return;

        Room room = user.getEntity().getRoom();

        if (room == null) return;

        UUID itemEntityId = UUID.fromString(body.getString("id"));

        Entity entity = room.getEntityById(itemEntityId);

        if (entity == null) return;

        ItemComponent itemComponent = (ItemComponent) entity.getComponent(ComponentType.Item);

        if (itemComponent == null) return;

        InteractionComponent interactionComponent =
                (InteractionComponent) entity.getComponent(ComponentType.Interaction);

        if (interactionComponent != null) {
            interactionComponent.onPickUp(user.getEntity());
        }

        Item item = itemComponent.getItem();

        roomService.pickUpItem(user, item, room);

        room.getUsers().sendBroadcastMessage(
                new ServerPacket(OutgoingPacketHeaders.RemoveRoomEntity, entity.getId().toString()));

    }
}
