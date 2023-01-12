package com.nhg.game.room.entity.component;

import com.nhg.game.item.Item;
import com.nhg.game.room.entity.Entity;
import lombok.Getter;
import lombok.NonNull;
import org.json.JSONException;
import org.json.JSONObject;

public class ItemComponent extends Component {

    @Getter
    private final Item item;

    public ItemComponent(@NonNull Item item) {
        this.item = item;
    }

    @Override
    public void setEntity(Entity entity) {
        if (this.entity != null) return;

        this.entity = entity;
        this.item.setEntity(entity);
    }

    @Override
    public JSONObject toJson() throws JSONException {
        return null;
    }

    @Override
    public void cycle() {

    }
}
