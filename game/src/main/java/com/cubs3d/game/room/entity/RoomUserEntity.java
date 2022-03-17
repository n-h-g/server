package com.cubs3d.game.room.entity;

import com.cubs3d.game.networking.message.outgoing.serverpackets.rooms.users.UpdatePosition;
import com.cubs3d.game.room.Room;
import com.cubs3d.game.user.User;
import lombok.Getter;
import org.json.JSONException;

public class RoomUserEntity extends RoomEntity {

    @Getter
    private final User user;

    public RoomUserEntity(Integer id, Room room, User user) {
        super(id, room);

        this.user = user;
        user.setEntity(this);
    }

    @Override
    protected void onPositionSet() {
        try {
            getRoom().getUsers().SendBroadcastMessage(new UpdatePosition(this.getPosition(), this.getBodyRotation()));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
