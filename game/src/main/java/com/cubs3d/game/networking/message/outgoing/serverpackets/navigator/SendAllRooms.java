package com.cubs3d.game.networking.message.outgoing.serverpackets.navigator;

import com.cubs3d.game.networking.message.outgoing.OutgoingPacketHeaders;
import com.cubs3d.game.networking.message.outgoing.ServerPacket;
import com.cubs3d.game.room.Room;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class SendAllRooms extends ServerPacket {

    public SendAllRooms(List<Room> rooms) throws JSONException {
        header = OutgoingPacketHeaders.SendAllRooms;

        JSONArray jsonRooms = new JSONArray();

        for (Room room : rooms) {
            jsonRooms.put(new JSONObject()
                    .put("id", room.getId())
                    .put("name", room.getName()));
        }

        body.put("rooms", jsonRooms);
    }
}
