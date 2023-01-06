package com.nhg.game.room.entity.component;

import com.nhg.game.utils.Gender;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONException;
import org.json.JSONObject;

public class HumanAspectComponent extends Component {

    @Getter
    @Setter
    private String look;

    @Getter
    @Setter
    private Gender gender;


    @Override
    public JSONObject toJson() throws JSONException {
        return new JSONObject()
                .put("look", look)
                .put("gender", gender);
    }

    @Override
    public void cycle() {

    }
}
