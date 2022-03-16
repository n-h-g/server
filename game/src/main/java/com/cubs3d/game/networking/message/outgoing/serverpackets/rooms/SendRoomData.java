package com.cubs3d.game.networking.message.outgoing.serverpackets.rooms;

import com.cubs3d.game.networking.message.outgoing.OutgoingPacketHeaders;
import com.cubs3d.game.networking.message.outgoing.ServerPacket;
import com.cubs3d.game.room.Room;

public class SendRoomData extends ServerPacket {

    public SendRoomData(Room room) {
        header = OutgoingPacketHeaders.SendRoomData;

    }
}
