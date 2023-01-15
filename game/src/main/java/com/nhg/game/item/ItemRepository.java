package com.nhg.game.item;

import com.nhg.game.room.Room;
import com.nhg.game.user.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends CrudRepository<Item, Integer> {
    List<Item> findByRoom(Room room);
    List<Item> findByOwner(User owner);

    @Modifying
    @Query(value = "UPDATE items SET owner_id = :userId, room_id = null WHERE room_id = :roomId", nativeQuery = true)
    void updateRoomItemsToUserItems(@Param("roomId") int roomId, @Param("userId") int userId);
}
