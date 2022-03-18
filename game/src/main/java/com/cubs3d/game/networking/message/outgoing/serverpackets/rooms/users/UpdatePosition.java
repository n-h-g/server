package com.cubs3d.game.networking.message.outgoing.serverpackets.rooms.users;

import com.cubs3d.game.networking.message.outgoing.OutgoingPacketHeaders;
import com.cubs3d.game.networking.message.outgoing.ServerPacket;
import com.cubs3d.game.room.entity.RoomEntity;
import com.cubs3d.game.user.User;
import lombok.NonNull;
import org.json.JSONException;

public class UpdatePosition extends ServerPacket {

    // TODO remove User
    public UpdatePosition(User user, @NonNull RoomEntity entity) throws JSONException {
        header = OutgoingPacketHeaders.UpdatePosition;

        body.put("userId", user.getId()); //TODO  body.put("id", entity.getId());
        body.put("x", entity.getPosition().getX());
        body.put("y", entity.getPosition().getY());
        body.put("rot", entity.getBodyRotation().getValue());
    }
}
