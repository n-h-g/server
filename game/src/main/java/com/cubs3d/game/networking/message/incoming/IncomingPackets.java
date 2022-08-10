package com.cubs3d.game.networking.message.incoming;

import com.cubs3d.game.networking.message.incoming.clientpackets.*;
import com.cubs3d.game.networking.message.incoming.clientpackets.friends.FriendsList;
import com.cubs3d.game.networking.message.incoming.clientpackets.navigator.*;
import com.cubs3d.game.networking.message.incoming.clientpackets.rooms.items.RequestLoadItems;
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
            new Pair(10, UserMove.class),
            new Pair(11, UserChatMessage.class),
            new Pair(12, UserTypeStatus.class),
            new Pair(13, UserLookAtPoint.class),
            new Pair(14, RequestLoadItems.class),
            new Pair(19, FriendsList.class)

   };

}