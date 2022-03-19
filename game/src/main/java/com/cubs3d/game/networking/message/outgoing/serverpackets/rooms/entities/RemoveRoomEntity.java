package com.cubs3d.game.networking.message.outgoing.serverpackets.rooms.entities;

import com.cubs3d.game.networking.message.outgoing.OutgoingPacketHeaders;
import com.cubs3d.game.networking.message.outgoing.ServerPacket;
import com.cubs3d.game.room.entity.RoomEntity;
import lombok.NonNull;
import org.json.JSONException;

public class RemoveRoomEntity extends ServerPacket {

    public RemoveRoomEntity(@NonNull RoomEntity entity) throws JSONException {
        header = OutgoingPacketHeaders.RemoveRoomEntity;

        body.put("id", entity.getId());
    }
}
