package com.nhg.game.room;

import com.nhg.game.user.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends CrudRepository<Room, Integer> {

    List<Room> findByOwner(User owner);

    @Query(value = "SELECT * FROM rooms WHERE id IN :ids", nativeQuery = true)
    List<Room> findByIds(@Param("ids") List<Integer> ids);

}
