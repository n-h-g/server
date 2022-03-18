package com.cubs3d.game.networking;

import com.cubs3d.game.networking.message.Packet;
import com.cubs3d.game.networking.message.outgoing.serverpackets.rooms.users.RemoveRoomUser;
import com.cubs3d.game.room.Room;
import com.cubs3d.game.room.RoomService;
import com.cubs3d.game.user.User;

import com.cubs3d.game.utils.ApplicationContextUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

public class WebSocketClient implements Client {

    private final WebSocketSession session;

    public WebSocketClient(WebSocketSession session) {
        this.session = session;
    }

    @Getter @Setter
    private User user;

    @Override
    public void sendMessage(@NonNull Packet<?,?> packet) {
        try {
            session.sendMessage(new TextMessage(packet.toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void disconnect() {
        try {
            this.unlinkUser();
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void linkUser(@NonNull User user) {
        user.setClient(this);
        this.user = user;
    }

    private void unlinkUser() {
        if (user == null) return;

        RoomService roomService = ApplicationContextUtils.getApplicationContext().getBean(RoomService.class);
        roomService.userExitRoom(user);

        user.setClient(null);
        user = null;
    }

}
