package com.nhg.game.npc.behaviour;

import com.nhg.game.networking.message.outgoing.OutgoingPacketHeaders;
import com.nhg.game.networking.message.outgoing.ServerPacket;
import com.nhg.game.npc.bot.Bot;
import com.nhg.game.room.Room;
import com.nhg.game.room.RoomService;
import com.nhg.game.room.entity.Entity;
import com.nhg.game.user.User;
import com.nhg.game.utils.BeanRetriever;
import com.nhg.game.utils.events.Event;
import com.nhg.game.utils.events.EventListener;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
public class WaiterBehaviour implements Behaviour, EventListener {

    private Room room;
    private Entity entity;

    public WaiterBehaviour(Bot bot) {
        RoomService roomService = BeanRetriever.get(RoomService.class);

        room = roomService.getActiveRoomById(bot.getRoom().getId());
        room.getEventHandler().subscribe(Event.USER_MESSAGE, this);
    }

    @Override
    public void onCycle(Entity self) {
        if (entity == null) {
            entity = self;
        }
    }

    @Override
    public void notify(Event event, Object... params) {
        try {
            User user = (User) params[0];
            String text = (String) params[1];

            //TODO give item
            sendMessage(text);

        } catch (Exception ignored) {}
    }

    @Scheduled(fixedDelay = 100)
    private void sendMessage(String text) throws JSONException {
        room.getUsers().sendBroadcastMessage(new ServerPacket(OutgoingPacketHeaders.RoomChatMessage, new JSONObject()
                .put("text", text)
                .put("authorId", entity.getId())
        ));
    }
}
