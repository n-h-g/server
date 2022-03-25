package com.cubs3d.game.item;

import com.cubs3d.game.room.Room;
import com.cubs3d.game.user.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends CrudRepository<Item, Integer> {
    List<Item> findByRoom(Room room);
    List<Item> findByOwner(User owner);
}
