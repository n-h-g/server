package com.nhg.game.room.entity.component;

import com.nhg.game.room.entity.Action;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;

public class ActionComponent extends Component {

    @Getter
    @Setter
    private Set<Action> actions;

    public ActionComponent() {
        this.actions = new HashSet<>();
    }

    /**
     * Add the given action to the entity.
     * If the action is <code>MOVE</code> it will remove all the actions that should be removed on move.
     *
     * @param action the action to add.
     * @see Action#shouldBeRemovedOnMove
     */
    public void addAction(Action action) {
        actions.add(action);

        if (action == Action.MOVE) {
            actions.removeIf(Action::shouldBeRemovedOnMove);
        }
    }

    public void removeAction(Action action) {
        this.actions.remove(action);
    }

    public boolean hasAction(Action action) {
        return this.actions.contains(action);
    }

    @Override
    public JSONObject toJson() throws JSONException {
        JSONArray actions = new JSONArray();
        this.actions.forEach(action -> actions.put(action.getValue()));

        return new JSONObject().put("actions", actions);
    }

    @Override
    public void cycle() {

    }
}
