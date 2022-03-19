package com.cubs3d.game.networking.message.incoming;

import com.cubs3d.game.networking.message.incoming.clientpackets.*;
import com.cubs3d.game.networking.message.incoming.clientpackets.navigator.*;
import com.cubs3d.game.networking.message.incoming.clientpackets.rooms.users.*;

public final class IncomingPackets {
    private IncomingPackets() {}

    public record Pair(Integer packetHeader, Class<? extends ClientPacket> packetClass) {}

    public static final Pair[] HeaderAndClassPairs = new Pair[] {
            new Pair(1, Handshake.class),
            new Pair(4, PingRequest.class),
            new Pair(6, GetAllRooms.class),
            new Pair(7, GetMyRooms.class),
            new Pair(8, UserEnterRoom.class),
            new Pair(9, UserExitRoom.class),
            new Pair(10, UserMove.class)
   };

}