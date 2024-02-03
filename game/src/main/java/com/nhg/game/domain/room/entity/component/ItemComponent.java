package com.nhg.game.domain.room.entity.component;

import com.nhg.game.domain.item.RoomItem
import lombok.Getter;
import lombok.NonNull;

@Getter
public class ItemComponent extends Component {

    private final RoomItem item;

    public ItemComponent(@NonNull RoomItem item) {
        this.item = item;
    }

    @Override
    public void cycle() {

    }
}
