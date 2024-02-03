package com.nhg.game.adapter.in.websocket.packet;

import com.nhg.game.adapter.in.websocket.packet.exchange.Handshake;
import com.nhg.game.adapter.in.websocket.packet.exchange.Ping;
import com.nhg.game.adapter.in.websocket.packet.navigator.AllRooms;
import com.nhg.game.adapter.in.websocket.packet.navigator.MyRooms;
import com.nhg.game.adapter.in.websocket.packet.room.CreateRoom;
import com.nhg.game.adapter.in.websocket.packet.room.user.UserEnterRoom;
import com.nhg.game.adapter.in.websocket.packet.room.user.UserExitRoom;
import com.nhg.game.adapter.in.websocket.packet.room.user.UserMove;
import com.nhg.game.adapter.in.websocket.packet.user.UpdateUser;
import com.nhg.game.infrastructure.networking.Client;
import com.nhg.game.infrastructure.networking.packet.ClientPacket;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.JSONObject;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class IncomingPacket implements ClientPacket<Integer, JSONObject> {

    protected Integer header;
    protected JSONObject body;
    protected Client<?> client;

    @Override
    public String toString() {
        return "{\"header\": " + this.getHeader() + ", \"body\": " + this.body.toString() + "}";
    }

    public static final Map<Integer, Class<? extends ClientPacket<Integer, JSONObject>>> HeaderClassMap =
            Map.ofEntries(
                    Map.entry(1, Handshake.class),
                    Map.entry(4, Ping.class),
                    Map.entry(6, AllRooms.class),
                    Map.entry(7, MyRooms.class),
                    Map.entry(8, UserEnterRoom.class),
                    Map.entry(9, UserExitRoom.class),
                    Map.entry(10, UserMove.class),
                    Map.entry(15, UpdateUser.class),
                    Map.entry(36, CreateRoom.class)
            );
}