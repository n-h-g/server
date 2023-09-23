package com.nhg.game.command.impl;

import com.nhg.game.command.Command;
import com.nhg.game.item.Item;
import com.nhg.game.item.ItemService;
import com.nhg.game.user.User;
import com.nhg.game.user.UserService;
import com.nhg.game.utils.BeanRetriever;
import lombok.NonNull;

public class GiveItemCommand implements Command {

    private final UserService userService;
    private final ItemService itemService;

    public GiveItemCommand() {
        userService = BeanRetriever.get(UserService.class);
        itemService = BeanRetriever.get(ItemService.class);
    }
    @Override
    public String getName() {
        return "item";
    }

    @Override
    public void execute(@NonNull User user, String... params) throws Exception {
        User itemReceiver = userService.getUserByUsername(params[0]);

        if (itemReceiver == null) return;

        String itemSpecName = params[1];

        Item item = itemService.itemFromSpecificationName(itemSpecName, itemReceiver);

        if (item == null) return;

        user.addItemToInventory(item);

        itemService.save(item);
    }
}
