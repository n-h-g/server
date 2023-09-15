package com.nhg.game.networking.message.incoming.clientpackets.rooms.items;

import com.nhg.game.item.ItemService;
import com.nhg.game.networking.WebSocketClient;
import com.nhg.game.networking.message.incoming.ClientPacket;
import com.nhg.game.room.Room;
import com.nhg.game.room.entity.Entity;
import com.nhg.game.room.entity.component.ComponentType;
import com.nhg.game.room.entity.component.InteractionComponent;
import com.nhg.game.user.User;
import com.nhg.game.utils.BeanRetriever;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public class UseItem extends ClientPacket {

    private ItemService itemService;

    public UseItem() {
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

            InteractionComponent interactionComponent =
                    (InteractionComponent) entity.getComponent(ComponentType.Interaction);

            if (interactionComponent != null) {
                interactionComponent.onClick(user.getEntity());
            }

        } catch (Exception e) {
            log.error("Error: "+ e);
            e.printStackTrace();
        }
    }
}
