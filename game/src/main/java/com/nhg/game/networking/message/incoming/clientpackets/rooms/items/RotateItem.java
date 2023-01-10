package com.nhg.game.networking.message.incoming.clientpackets.rooms.items;

import com.nhg.game.item.Item;
import com.nhg.game.item.ItemService;
import com.nhg.game.networking.WebSocketClient;
import com.nhg.game.networking.message.incoming.ClientPacket;
import com.nhg.game.networking.message.outgoing.OutgoingPacketHeaders;
import com.nhg.game.networking.message.outgoing.ServerPacket;
import com.nhg.game.room.Room;
import com.nhg.game.room.entity.Entity;
import com.nhg.game.room.entity.component.ComponentType;
import com.nhg.game.room.entity.component.ItemComponent;
import com.nhg.game.room.entity.component.RotationComponent;
import com.nhg.game.user.User;
import com.nhg.game.utils.BeanRetriever;
import com.nhg.game.utils.Rotation;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public class RotateItem extends ClientPacket {

    private ItemService itemService;

    public RotateItem() {
        itemService = BeanRetriever.get(ItemService.class);
    }

    @Override
    public void handle() {
        try {
            WebSocketClient wsClient = (WebSocketClient) client;
            User user = wsClient.getUser();

            if (user == null) return;

            Room room = user.getEntity().getRoom();

            if (room == null) return;

            UUID itemEntityId = UUID.fromString(body.getString("id"));

            Entity entity = room.getEntityById(itemEntityId);

            if (entity == null) return;

            ItemComponent itemComponent = (ItemComponent) entity.getComponent(ComponentType.Item);
            RotationComponent rotationComponent = (RotationComponent) entity.getComponent(ComponentType.Rotation);

            if (itemComponent == null || rotationComponent == null) return;

            Rotation rotation = rotationComponent.rotate();

            Item item = itemComponent.getItem();
            item.setRotation(rotation);

            itemService.save(item);

            room.getUsers().sendBroadcastMessage(
                    new ServerPacket(OutgoingPacketHeaders.UpdateEntity, entity.getId().toString()));


        } catch (Exception e) {
            log.error("Error: "+ e);
            e.printStackTrace();
        }
    }
}
