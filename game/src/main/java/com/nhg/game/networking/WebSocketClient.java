package com.nhg.game.networking;

import com.nhg.game.networking.message.Packet;
import com.nhg.game.room.RoomService;
import com.nhg.game.user.User;

import com.nhg.game.user.UserService;
import com.nhg.game.utils.ApplicationContextUtils;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

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

    /**
     * Create an association Client-User.
     *
     * @param user the user to associate with the client.
     */
    public void linkUser(@NonNull User user) {
        user.setClient(this);

        UserService userService = ApplicationContextUtils.getApplicationContext().getBean(UserService.class);
        userService.userJoin(user);
        
        this.user = user;
    }

    /**
     * Unlink the association Client-User and, if the user is in a room, remove he's entity.
     * 
     * @see RoomService#userExitRoom(User) 
     */
    private void unlinkUser() {
        if (user == null) return;

        RoomService roomService = ApplicationContextUtils.getApplicationContext().getBean(RoomService.class);
        UserService userService = ApplicationContextUtils.getApplicationContext().getBean(UserService.class);
        roomService.userExitRoom(user);

        userService.userLeave(user);
        user.setClient(null);
        user = null;
    }

}
