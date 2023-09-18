package com.nhg.game.npc.behaviour;

import com.nhg.game.npc.bot.Bot;
import com.nhg.game.room.entity.Entity;
import lombok.NonNull;

public interface Behaviour {

    default void onCycle(Entity self) {}

    static Behaviour fromBot(@NonNull Bot bot) {
        return new RandomMovementBehaviour(bot);
    }
}
