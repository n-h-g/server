package com.nhg.game.room.entity.component;

import com.nhg.game.npc.behaviour.Behaviour;
import org.json.JSONException;
import org.json.JSONObject;

public class BehaviourComponent extends Component {

    private final Behaviour behaviour;
    public BehaviourComponent(Behaviour behaviour) {
        this.behaviour = behaviour;
    }
    @Override
    public JSONObject toJson() throws JSONException {
        return null;
    }

    @Override
    public void cycle() {
        behaviour.cycle(entity);
    }
}
