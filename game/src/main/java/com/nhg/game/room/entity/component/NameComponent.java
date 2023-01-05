package com.nhg.game.room.entity.component;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.json.JSONException;
import org.json.JSONObject;

public class NameComponent extends Component {

    @Getter
    @Setter
    private String name;

    public NameComponent(@NonNull String name) {
        this.name = name;
    }

    @Override
    public JSONObject toJson() throws JSONException {
        return new JSONObject()
                .put("name", name);
    }

    @Override
    public void cycle() {

    }
}
