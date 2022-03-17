package com.cubs3d.game.networking.message.outgoing.serverpackets.rooms.users;

import com.cubs3d.game.networking.message.outgoing.OutgoingPacketHeaders;
import com.cubs3d.game.networking.message.outgoing.ServerPacket;
import com.cubs3d.game.room.Room;
import com.cubs3d.game.user.UserGroup;
import org.json.JSONException;

public class LoadUsersInRoom extends ServerPacket {

        public LoadUsersInRoom(Room room) throws JSONException {
            header = OutgoingPacketHeaders.LoadUsersInRoom;

            UserGroup users = room.getUsers();
        }
}
