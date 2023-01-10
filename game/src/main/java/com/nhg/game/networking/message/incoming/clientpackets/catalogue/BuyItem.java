package com.nhg.game.networking.message.incoming.clientpackets.catalogue;

import com.nhg.game.dto.CatalogueItem;
import com.nhg.game.item.Item;
import com.nhg.game.item.ItemService;
import com.nhg.game.networking.WebSocketClient;
import com.nhg.game.networking.message.incoming.ClientPacket;
import com.nhg.game.networking.message.outgoing.OutgoingPacketHeaders;
import com.nhg.game.networking.message.outgoing.ServerPacket;
import com.nhg.game.user.User;
import com.nhg.game.user.UserService;
import com.nhg.game.utils.BeanRetriever;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class BuyItem extends ClientPacket {

    private final RestTemplate restTemplate;
    private final UserService userService;
    private final ItemService itemService;

    public BuyItem() {
        restTemplate = BeanRetriever.get("restTemplate", RestTemplate.class);
        userService = BeanRetriever.get("userService", UserService.class);
        itemService = BeanRetriever.get("itemService", ItemService.class);
    }

    @Override
    public void handle() {
        try {
            WebSocketClient wsClient = (WebSocketClient) client;
            User user = wsClient.getUser();

            if (user == null) return;

            int itemId = body.getInt("id");

            CatalogueItem catalogueItem = restTemplate.getForEntity(
                    "http://CATALOGUE/api/v1/catalogue/item/{itemId}",
                    CatalogueItem.class,
                    itemId
            ).getBody();

            if (catalogueItem == null) return;

            if (user.getCredits() < catalogueItem.credits()) {
                client.sendMessage(new ServerPacket(OutgoingPacketHeaders.NotEnoughCredits));
                return;
            }

            Item item = itemService.itemFromCataloguePurchase(catalogueItem, user);

            if (item == null) return;

            user.updateCredits(-catalogueItem.credits());
            user.addItemToInventory(item);

            itemService.save(item);
            userService.save(user);

            client.sendMessage(new ServerPacket(OutgoingPacketHeaders.BuyItem, item));

        } catch(Exception e) {
            log.error("Error: "+ e);
            e.printStackTrace();
        }
    }
}
