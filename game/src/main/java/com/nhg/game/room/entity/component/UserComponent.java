package com.nhg.game.room.entity.component;

import com.nhg.game.room.entity.Entity;
import com.nhg.game.user.User;
import lombok.NonNull;
import org.json.JSONException;
import org.json.JSONObject;

public class UserComponent extends Component {

    private final User user;

    public UserComponent(@NonNull User user) {
        this.user = user;
    }

    @Override
    public void setEntity(Entity entity) {
        if (this.entity != null) return;

        this.entity = entity;
        this.user.setEntity(entity);
    }

    @Override
    public void cycle() {

    }

    @Override
    public JSONObject toJson() throws JSONException {
        return new JSONObject()
                .put("id", user.getId());
    }
}