package com.nhg.game.room.entity.component;

import lombok.Getter;
import lombok.Setter;
import org.json.JSONException;
import org.json.JSONObject;

public class NameComponent extends Component {

    @Getter
    @Setter
    private String name;

    @Override
    public JSONObject toJson() throws JSONException {
        return new JSONObject()
                .put("name", name);
    }

    @Override
    public void cycle() {

    }
}
