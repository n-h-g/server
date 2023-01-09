package com.nhg.game.networking.message.incoming;

import com.nhg.game.networking.message.incoming.clientpackets.Handshake;
import com.nhg.game.networking.message.incoming.clientpackets.PingRequest;
import com.nhg.game.networking.message.incoming.clientpackets.items.InventoryItems;
import com.nhg.game.networking.message.incoming.clientpackets.navigator.AllRooms;
import com.nhg.game.networking.message.incoming.clientpackets.navigator.MyRooms;
import com.nhg.game.networking.message.incoming.clientpackets.rooms.CreateRoom;
import com.nhg.game.networking.message.incoming.clientpackets.rooms.SaveRoomSettings;
import com.nhg.game.networking.message.incoming.clientpackets.rooms.items.PickupItem;
import com.nhg.game.networking.message.incoming.clientpackets.rooms.users.UserChatMessage;
import com.nhg.game.networking.message.incoming.clientpackets.rooms.users.UserEnterRoom;
import com.nhg.game.networking.message.incoming.clientpackets.rooms.users.UserExitRoom;
import com.nhg.game.networking.message.incoming.clientpackets.rooms.users.UserLookAtPoint;
import com.nhg.game.networking.message.incoming.clientpackets.rooms.users.UserMove;
import com.nhg.game.networking.message.incoming.clientpackets.rooms.users.UserTypeStatus;
import com.nhg.game.networking.message.incoming.clientpackets.users.UpdateUser;

public final class IncomingPackets {
    private IncomingPackets() {}

    public record Pair(Integer packetHeader, Class<? extends ClientPacket> packetClass) {}

    public static final Pair[] HeaderAndClassPairs = new Pair[] {
            new Pair(1, Handshake.class),
            new Pair(4, PingRequest.class),
            new Pair(6, AllRooms.class),
            new Pair(7, MyRooms.class),
            new Pair(8, UserEnterRoom.class),
            new Pair(9, UserExitRoom.class),
            new Pair(10, UserMove.class),
            new Pair(11, UserChatMessage.class),
            new Pair(12, UserTypeStatus.class),
            new Pair(13, UserLookAtPoint.class),
            new Pair(14, InventoryItems.class),
            new Pair(15, UpdateUser.class),
            new Pair(23, PickupItem.class),
            new Pair(36, CreateRoom.class),
            new Pair(44, SaveRoomSettings.class)
   };

}