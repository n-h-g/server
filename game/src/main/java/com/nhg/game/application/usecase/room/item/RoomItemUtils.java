package com.nhg.game.application.usecase.room.item;

import com.nhg.game.application.exception.ProblemCode;
import com.nhg.game.application.exception.UseCaseException;
import com.nhg.game.application.repository.ItemRepository;
import com.nhg.game.application.usecase.room.RoomConstants;
import com.nhg.game.application.usecase.room.RoomUtils;
import com.nhg.game.domain.item.ItemPrototype;
import com.nhg.game.domain.item.ItemType;
import com.nhg.game.domain.item.RoomItem;
import com.nhg.game.domain.room.Room;
import com.nhg.game.domain.room.layout.Tile;
import com.nhg.game.domain.shared.position.Position2;
import com.nhg.game.domain.shared.position.Position3;
import com.nhg.game.domain.shared.position.Rotation;
import lombok.NonNull;

import java.util.HashSet;
import java.util.Set;

public final class RoomItemUtils {

    public static void moveItemAtOrThrow(@NonNull Room room,
                                         @NonNull RoomItem item,
                                         @NonNull Position2 position,
                                         @NonNull ItemRepository itemRepository) throws UseCaseException {
        if (item.getPrototype().getItemType() == ItemType.FLOOR_ITEM) {
            moveFloorItemAtOrThrow(room, item, position, itemRepository);
        } else {
            placeWallItemAtOrThrow(room, item, position, itemRepository);
        }

        itemRepository.save(item);
    }

    private static void moveFloorItemAtOrThrow(Room room, RoomItem item, Position2 position,
                                               ItemRepository itemRepository) throws UseCaseException {
        ItemPrototype prototype = item.getPrototype();

        float height = getHeightAtPositionForItem(room, item, position, itemRepository);
        Position3 newPosition = new Position3(position, height);

        canPlaceItemAtOrThrow(room, newPosition);

        item.setPosition(newPosition);

        Tile.State state = prototype.isAllowWalk() ? Tile.State.OPEN : Tile.State.CLOSE;

        RoomUtils.updateRoomLayoutAt(room.getRoomLayout(), position, state, height);
    }

    private static void placeWallItemAtOrThrow(Room room, RoomItem item, Position2 position,
                                               ItemRepository itemRepository) throws UseCaseException {
        // TODO
    }

    private static float getHeightAtPositionForItem(Room room, RoomItem item,
                                                    Position2 position, ItemRepository itemRepository) {
        Set<Position2> affectedPositions = getPositionsAffectedByItem(item.getPrototype(), position, item.getRotation());

        return affectedPositions.stream()
                .map(p -> getHeightAtPosition(room, itemRepository, p))
                .max(Float::compareTo)
                .orElseThrow();
    }

    private static float getHeightAtPosition(Room room, ItemRepository itemRepository, Position2 position) {
        RoomItem highestItem = itemRepository.getHighestItemAtPosition(room.getId(), position);

        return (highestItem == null) ?
                room.getRoomLayout().getTile(position).getPosition().getZ() :
                highestItem.getPosition().getZ();
    }

    private static Set<Position2> getPositionsAffectedByItem(ItemPrototype itemPrototype,
                                                             Position2 position, Rotation rotation) {
        Set<Position2> affectedPositions = new HashSet<>();

        int x = position.getX();
        int y = position.getY();
        int xGrow = itemPrototype.getWidth();
        int yGrow = itemPrototype.getLength();

        if (rotation == Rotation.NORTH || rotation == Rotation.SOUTH) {
            xGrow = itemPrototype.getLength();
            yGrow = itemPrototype.getWidth();
        }

        for (int i = 0; i < xGrow; i++) {
            for (int j = 0; j < yGrow; j++) {
                affectedPositions.add(new Position2(x + i, y + j));
            }
        }

        return affectedPositions;
    }

    private static void canPlaceItemAtOrThrow(Room room, Position3 position) throws UseCaseException {
        if (room.getEntities().itemsCount() == RoomConstants.MAX_ITEMS)
            throw new UseCaseException(ProblemCode.MAX_ITEMS);

        if (position.getZ() > RoomConstants.MAX_HEIGHT)
            throw new UseCaseException(ProblemCode.MAX_HEIGHT);
    }

}
