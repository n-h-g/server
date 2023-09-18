package com.nhg.game.networking.message.incoming.clientpackets.rooms.items;

import com.nhg.game.item.ItemService;
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
import com.nhg.game.room.entity.component.PositionComponent;
import com.nhg.game.shared.PersistentPosition;
import com.nhg.game.user.User;
import com.nhg.game.utils.BeanRetriever;
import com.nhg.game.utils.Position3;

import java.util.UUID;

public class MoveItem extends ClientPacket {

    private RoomService roomService;
    private ItemService itemService;

    public MoveItem() {
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

        UUID itemEntityId = UUID.fromString(body.getString("id"));

        int x = body.getInt("x");
        int y = body.getInt("y");
        int z = body.getInt("z");

        Entity entity = room.getEntityById(itemEntityId);

        if (entity == null) return;

        ItemComponent itemComponent = (ItemComponent) entity.getComponent(ComponentType.Item);
        PositionComponent positionComponent = (PositionComponent) entity.getComponent(ComponentType.Position);

        if (itemComponent == null) return;

        positionComponent.setPosition(new Position3(x, y, z));

        PersistentPosition persistentPosition = new PersistentPosition();
        persistentPosition.setX(x);
        persistentPosition.setY(y);
        persistentPosition.setZ(z);
        itemComponent.getItem().setPosition(persistentPosition);

        roomService.updateItem(itemComponent.getItem(), room);
        itemService.userPlaceItem(user, itemComponent.getItem(), room);

        itemService.save(itemComponent.getItem());

        InteractionComponent interactionComponent =
                (InteractionComponent) entity.getComponent(ComponentType.Interaction);

        if (interactionComponent != null) {
            interactionComponent.onMove(user.getEntity());
        }

        room.getUsers().sendBroadcastMessage(
                new ServerPacket(OutgoingPacketHeaders.UpdateEntity, entity));
    }
}
