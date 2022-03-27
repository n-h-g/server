package com.cubs3d.game.item;

import com.cubs3d.game.user.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

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
}
