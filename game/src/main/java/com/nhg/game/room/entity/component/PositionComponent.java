package com.nhg.game.room.entity.component;

import com.nhg.game.utils.Int3;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONException;
import org.json.JSONObject;

public class PositionComponent extends Component {

    @Getter
    @Setter
    private Int3 position;

    public PositionComponent() {
        position = Int3.Zero;
    }

    public PositionComponent(Int3 position) {
        this.position = position;
    }

    @Override
    public void cycle() {

    }

    @Override
    public JSONObject toJson() throws JSONException {
        return new JSONObject()
                .put("x", position.getX())
                .put("y", position.getY())
                .put("z", position.getZ());
    }
}
