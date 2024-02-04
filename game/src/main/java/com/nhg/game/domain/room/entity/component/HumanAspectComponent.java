package com.nhg.game.domain.room.entity.component;

import com.nhg.game.domain.shared.human.Gender;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HumanAspectComponent extends Component {


    private String look;
    private Gender gender;

    public HumanAspectComponent(String look, Gender gender) {
        this.look = look;
        this.gender = gender;
    }

}
