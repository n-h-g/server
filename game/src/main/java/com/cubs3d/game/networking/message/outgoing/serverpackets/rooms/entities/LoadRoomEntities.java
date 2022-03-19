package com.cubs3d.game.networking.message.outgoing.serverpackets.rooms.entities;

import com.cubs3d.game.networking.message.outgoing.OutgoingPacketHeaders;
import com.cubs3d.game.networking.message.outgoing.ServerPacket;
import com.cubs3d.game.room.Room;
import com.cubs3d.game.room.entity.RoomEntity;
import lombok.NonNull;
import org.json.JSONArray;
import org.json.JSONException;

public class LoadRoomEntities extends ServerPacket {

    public LoadRoomEntities(@NonNull Room room) throws JSONException {
        header = OutgoingPacketHeaders.LoadRoomEntities;

        JSONArray entities = new JSONArray();

        for (RoomEntity entity : room.getEntities().values()) {
            entities.put(entity.toJsonObject());
        }

        body.put("entities", entities);

    }
}
