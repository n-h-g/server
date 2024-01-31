package com.nhg.game.domain.room.entity.component;

import com.nhg.game.domain.room.entity.Action;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class ActionComponent extends Component {

    private Set<Action> actions;

    public ActionComponent() {
        this.actions = new HashSet<>();
    }

    /**
     * Add the given action to the entity.
     * If the action is <code>MOVE</code> it will remove all the actions that should be removed on move.
     *
     * @param action the action to add.
     * @see Action#shouldBeRemovedOnMove()
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
    public void cycle() {

    }
}
