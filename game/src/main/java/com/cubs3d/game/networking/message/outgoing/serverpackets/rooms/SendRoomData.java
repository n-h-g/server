package com.cubs3d.game.networking.message.outgoing.serverpackets.rooms;

import com.cubs3d.game.networking.message.outgoing.OutgoingPacketHeaders;
import com.cubs3d.game.networking.message.outgoing.ServerPacket;
import com.cubs3d.game.room.Room;
import lombok.NonNull;
import org.json.JSONException;

public class SendRoomData extends ServerPacket {

    public SendRoomData(@NonNull Room room) throws JSONException {
        header = OutgoingPacketHeaders.SendRoomData;

        body.put("id", room.getId());
        body.put("name", room.getName());
        body.put("layout", room.getLayout());
        body.put("doorx", 0);
        body.put("doory", 0);

    }
}
