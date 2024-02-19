package com.nhg.game.application.usecase.room.item;

import com.nhg.game.application.exception.ProblemCode;
import com.nhg.game.application.exception.UseCaseException;
import com.nhg.game.application.usecase.room.RoomConstants;
import com.nhg.game.application.usecase.room.RoomUtils;
import com.nhg.game.domain.item.ItemPrototype;
import com.nhg.game.domain.item.ItemType;
import com.nhg.game.domain.item.RoomItem;
import com.nhg.game.domain.room.Room;
import com.nhg.game.domain.room.layout.Tile;
import com.nhg.game.domain.shared.position.Position2;
import com.nhg.game.domain.shared.position.Position3;

public final class RoomItemUtils {

    public static void moveItemAtOrThrow(Room room, RoomItem item, Position2 position) throws UseCaseException {
        if (item.getPrototype().getItemType() == ItemType.FLOOR_ITEM) {
            moveFloorItemAtOrThrow(room, item, position);
            return;
        }

        placeWallItemAtOrThrow(room, item, position);
    }

    private static void moveFloorItemAtOrThrow(Room room, RoomItem item, Position2 position) throws UseCaseException {
        ItemPrototype prototype = item.getPrototype();

        float height = getHeightAtPositionForItem(room, prototype, position);
        Position3 newPosition = new Position3(position, height);

        canPlaceItemAtOrThrow(room, newPosition);

        item.setPosition(newPosition);

        Tile.State state = prototype.isAllowWalk() ? Tile.State.OPEN : Tile.State.CLOSE;

        RoomUtils.updateRoomLayoutAt(room.getRoomLayout(), position, state, height);
    }

    private static void placeWallItemAtOrThrow(Room room, RoomItem item, Position2 position) throws UseCaseException {
        // TODO
    }

    private static float getHeightAtPositionForItem(Room room, ItemPrototype itemPrototype, Position2 position) {
        // TODO: heightmap
        return room.getRoomLayout().getTile(position).getPosition().getZ();
    }

    private static void canPlaceItemAtOrThrow(Room room, Position3 position) throws UseCaseException {
        if (room.getEntities().itemsCount() == RoomConstants.MAX_ITEMS)
            throw new UseCaseException(ProblemCode.MAX_ITEMS);

        if (position.getZ() > RoomConstants.MAX_HEIGHT)
            throw new UseCaseException(ProblemCode.MAX_HEIGHT);
    }

}
