package com.nhg.game.room.entity.component;

import com.nhg.game.npc.behaviour.Behaviour;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class BehaviourComponent extends Component {

    private final List<Behaviour> behaviours;
    public BehaviourComponent(List<Behaviour> behaviours) {
        this.behaviours = behaviours;
    }
    @Override
    public JSONObject toJson() throws JSONException {
        return null;
    }

    @Override
    public void cycle() {
        for (Behaviour behaviour : behaviours) {
            behaviour.onCycle(entity);
        }
    }
}
