package com.nhg.game.domain.user;


import com.nhg.game.domain.shared.human.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class User {

    private Integer id;
    private String username;
    private String motto;
    private Gender gender;
    private String look;
    private int credits;
}
