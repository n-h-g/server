package com.nhg.game.item;

import com.nhg.game.room.Room;
import com.nhg.game.user.User;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<Item> getItemsByOwner(User owner) { return this.itemRepository.findByOwner(owner); }


    public List<Item> getItemsForRoom(@NonNull Room room) {
        return itemRepository.findByRoom(room);
    }

}
