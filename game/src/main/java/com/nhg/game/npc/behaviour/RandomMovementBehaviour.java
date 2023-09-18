package com.nhg.game.npc.behaviour;

import com.nhg.game.networking.message.outgoing.OutgoingPacketHeaders;
import com.nhg.game.networking.message.outgoing.ServerPacket;
import com.nhg.game.npc.bot.Bot;
import com.nhg.game.room.Room;
import com.nhg.game.room.RoomService;
import com.nhg.game.room.entity.Entity;
import com.nhg.game.room.entity.component.ComponentType;
import com.nhg.game.room.entity.component.MovementComponent;
import com.nhg.game.room.entity.component.PositionComponent;
import com.nhg.game.user.User;
import com.nhg.game.utils.BeanRetriever;
import com.nhg.game.utils.Int2;
import com.nhg.game.utils.events.Event;
import com.nhg.game.utils.events.EventListener;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;

import java.util.Random;

@Slf4j
public class RandomMovementBehaviour implements Behaviour, EventListener {

    private static final int range = 15;
    private static final long executeEveryMs = 600;
    private final Random random;
    private long lastExecutionTime = 0;
    private Room room;
    private Entity entity;

    public RandomMovementBehaviour(Bot bot) {
         this.random = new Random();

        RoomService roomService = BeanRetriever.get(RoomService.class);

        room = roomService.getActiveRoomById(bot.getRoom().getId());
        room.getEventHandler().subscribe(Event.USER_MESSAGE, this);
    }

    @Override
    public void onCycle(Entity self) {
        if (entity == null) {
            entity = self;
        }
        long currentTime = System.currentTimeMillis();

        if (currentTime < lastExecutionTime + executeEveryMs) return;

        lastExecutionTime = currentTime;

        PositionComponent positionComponent = (PositionComponent) self.getComponent(ComponentType.Position);
        MovementComponent movementComponent = (MovementComponent) self.getComponent(ComponentType.Movement);

        if (positionComponent == null || movementComponent == null) return;

        movementComponent.setDestination(randomDestination(positionComponent.getPosition().ToInt2XY()));
        movementComponent.calculatePath();
    }

    private Int2 randomDestination(Int2 currentPosition) {
        int randX = random.nextInt(range * 2 + 1) - range;
        int randY = random.nextInt(range * 2 + 1) - range;

        return new Int2(
                currentPosition.getX() + randX,
                currentPosition.getY() + randY
        );
    }

    @Override
    public void notify(Event event, Object... obj) {
        User user = (User) obj[0];
        String text = (String) obj[1];
        try {
            room.getUsers().sendBroadcastMessage(new ServerPacket(OutgoingPacketHeaders.RoomChatMessage, new JSONObject()
                    .put("text", text)
                    .put("authorId", entity.getId())
                )
            );
        } catch (Exception e) {
            log.error(e.toString());
        }

        log.warn(text);
    }
}
