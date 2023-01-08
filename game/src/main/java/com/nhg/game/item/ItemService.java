package com.nhg.game.item;

import com.nhg.game.room.Room;
import com.nhg.game.user.User;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Service
public class ItemService {

    private final ItemRepository itemRepository;


    /**
     * Get the items owned by the given user.
     *
     * @param owner items' owner
     * @return items owned by the given user.
     */
    public List<Item> getItemsByOwner(@NonNull User owner) { return this.itemRepository.findByOwner(owner); }

    public List<Item> getItemsForRoom(@NonNull Room room) {
        return itemRepository.findByRoom(room);
    }

    public Item getItemByIdAndOwner(@NonNull User owner, int itemId) {
        Optional<Item> optItem = itemRepository.findById(itemId);

        if (optItem.isPresent() && optItem.get().getOwner() == owner) {
            return optItem.get();
        }

        return null;
    }

    public void userPickUpItem(@NonNull User user, @NonNull Item item) {
        item.setOwner(user);
        user.addItemToInventory(item);

        save(item);
    }

    public void userPlaceItem(@NonNull User user, @NonNull Item item) {
        item.setOwner(null);
        user.removeItemFromInventory(item);

        save(item);
    }

    public void save(@NonNull Item item) {
        itemRepository.save(item);
    }

}
