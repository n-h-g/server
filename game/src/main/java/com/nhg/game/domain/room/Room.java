package com.nhg.game.domain.room;


import com.nhg.game.domain.room.layout.RoomLayout;
import com.nhg.game.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Room implements Runnable {

    private int id;
    private String name;
    private String description;
    private User owner;
    private RoomLayout roomLayout;

    public Room() {
    }

    public Room(String name, String description, User owner, String layout, int doorX, int doorY, int doorRotation) {
        this();

        this.name = name;
        this.description = description;
        this.owner = owner;
        this.roomLayout = new RoomLayout(layout, doorX, doorY, doorRotation);
    }

    public boolean isEmpty() {
        return true;
    }

    @Override
    public void run() {

    }
}
