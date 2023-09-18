package com.nhg.game.npc.behaviour;

import com.nhg.game.npc.bot.Bot;
import com.nhg.game.room.entity.Entity;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

public interface Behaviour {

    default void onCycle(Entity self) {}

    static List<Behaviour> fromBot(@NonNull Bot bot) {
        List<Behaviour> behaviours = new ArrayList<>();

        behaviours.add(new RandomMovementBehaviour());

        return behaviours;
    }
}
