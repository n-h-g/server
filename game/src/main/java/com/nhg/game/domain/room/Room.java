package com.nhg.game.domain.room;


import com.nhg.game.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Room implements Runnable {
    private Integer id;
    private String name;
    private String description;
    private User owner;

    public boolean isEmpty() {
        return true;
    }

    @Override
    public void run() {

    }
}
