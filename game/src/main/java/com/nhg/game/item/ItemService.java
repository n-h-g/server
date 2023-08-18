package com.nhg.game.item;

import com.nhg.game.dto.CatalogueItem;
import com.nhg.game.room.Room;
import com.nhg.game.user.User;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemSpecificationRepository itemSpecificationRepository;


    /**
     * (Create) If an item with a null id is passed then it will create a new item and generate an id for it.
     * (Update) If the item already have an id it will update the existing item with the params of the given item.
     *
     * @param item item to save
     * @return the saved item
     */
    public Item save(@NonNull Item item) {
        return itemRepository.save(item);
    }

    /**
     * Get the items owned by the given user.
     *
     * @param owner items' owner.
     * @return items owned by the given user.
     */
    public List<Item> getItemsByOwner(@NonNull User owner) { return this.itemRepository.findByOwner(owner); }

    /**
     * Get the items contained in the given room.
     *
     * @param room the room where the item is placed.
     * @return items contained in the given room.
     */
    public List<Item> getItemsByRoom(@NonNull Room room) {
        return itemRepository.findByRoom(room);
    }

    /**
     * Get the item with the given id if it's also owned by the given user.
     *
     * @param owner item's owner.
     * @param itemId item's id.
     * @return the item with the given id and owned by the given user.
     */
    public Item getItemByIdAndOwner(@NonNull User owner, int itemId) {
        Optional<Item> optItem = itemRepository.findById(itemId);

        if (optItem.isPresent() && Objects.equals(optItem.get().getOwner().getId(), owner.getId())) {
            return optItem.get();
        }

        return null;
    }

    /**
     * @param name the item specification name.
     * @return the item specification with the given name.
     */
    public ItemSpecification getItemSpecificationByName(String name) {
        return itemSpecificationRepository.findByName(name);
    }

    /**
     * It is used to create an item after it's purchase from catalogue.
     * The item created will have the given user as the owner.
     *
     * @param catalogueItem catalogue item dto.
     * @param user the user to be set as the item's owner.
     * @return the item created.
     * @see #getItemSpecificationByName 
     */
    public Item itemFromCataloguePurchase(@NonNull CatalogueItem catalogueItem, @NonNull User user) {
        ItemSpecification itemSpec = getItemSpecificationByName(catalogueItem.name());

        if (itemSpec == null) return null;

        return new Item(itemSpec, user);

    }

    /**
     * It is used when an item is picked up from the room.
     * It sets the user that picked the item as the owner of it
     * and then add the item to the user's inventory.
     *
     * @param user the user that picked up the item.
     * @param item the item picked up.
     * @see User#addItemToInventory
     * @see #save
     */
    @Transactional
    public void userPickUpItem(@NonNull User user, @NonNull Item item) {
        item.setOwner(user);
        item.setRoom(null);

        user.addItemToInventory(item);

        save(item);
    }

    /**
     * It is used when an item is placed on a room.
     * It unsets the item's owner, removes the item from the user's inventory
     * and sets the item's room.
     *
     * @param user the user that placed the item.
     * @param item the item placed.
     * @param room the room where the item is placed.
     * @see User#removeItemFromInventory
     * @see #save
     */
    @Transactional
    public void userPlaceItem(@NonNull User user, @NonNull Item item, @NonNull Room room) {
        item.setOwner(null);
        item.setRoom(room);

        user.removeItemFromInventory(item);

        save(item);
    }

    /**
     * All the items contained in the room retrieves to the room owner.
     * It sets as the items owner the room's owner and removes the items from the room.
     *
     * @param room the target room where the items are retrieved to the owner.
     */
    public void retrieveRoomItemsToRoomOwner(@NonNull Room room) {
        itemRepository.updateRoomItemsToUserItems(room.getId(), room.getOwner().getId());
    }

}
