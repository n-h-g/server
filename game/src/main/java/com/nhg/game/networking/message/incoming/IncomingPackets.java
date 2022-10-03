package com.nhg.game.networking.message.incoming;

import com.nhg.game.networking.message.incoming.clientpackets.*;
import com.nhg.game.networking.message.incoming.clientpackets.friends.*;
import com.nhg.game.networking.message.incoming.clientpackets.rooms.CreateRoom;
import com.nhg.game.networking.message.incoming.clientpackets.rooms.items.RoomPickupItem;
import com.nhg.game.networking.message.incoming.clientpackets.rooms.items.RoomPlaceItem;
import com.nhg.game.networking.message.incoming.clientpackets.navigator.*;
import com.nhg.game.networking.message.incoming.clientpackets.items.RequestLoadItems;
import com.nhg.game.networking.message.incoming.clientpackets.rooms.users.*;
import com.nhg.game.networking.message.incoming.clientpackets.users.UpdateUser;

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
            new Pair(15, UpdateUser.class),
            new Pair(16, AddFriendship.class),
            new Pair(17, AcceptFriendRequestEvent.class),
            new Pair(18, RemoveFriendship.class),
            new Pair(19, FriendsList.class),
            new Pair(20, SearchUserEvent.class),
            new Pair(23, RoomPickupItem.class),
            new Pair(24, RoomPlaceItem.class),
            new Pair(36, CreateRoom.class),
            new Pair(39, FriendPrivateMessageEvent.class)

   };

}