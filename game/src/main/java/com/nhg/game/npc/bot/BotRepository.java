package com.nhg.game.npc.bot;

import com.nhg.game.room.Room;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BotRepository extends CrudRepository<Bot, Integer> {

    List<Bot> findByRoom(Room room);
}
