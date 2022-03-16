package com.cubs3d.game.networking.message.outgoing.serverpackets.rooms;

import com.cubs3d.game.networking.message.outgoing.OutgoingPacketHeaders;
import com.cubs3d.game.networking.message.outgoing.ServerPacket;
import com.cubs3d.game.room.Room;
import org.json.JSONException;

public class SendRoomData extends ServerPacket {

    public SendRoomData(Room room) throws JSONException {
        header = OutgoingPacketHeaders.SendRoomData;

    }
}
