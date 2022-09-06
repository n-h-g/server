package com.nhg.game.item;

import com.nhg.game.room.Room;
import com.nhg.game.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends CrudRepository<Item, Integer> {
    List<Item> findByRoom(Room room);
    List<Item> findByOwner(User owner);
}
