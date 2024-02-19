package com.nhg.game.adapter.out.persistence.inmemory;

import com.nhg.game.domain.item.RoomItem;
import com.nhg.game.domain.shared.position.Position2;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

@Component
public class RoomItemInMemoryCache {

    private final Map<Integer, RoomItemCache> roomsCache = new ConcurrentHashMap<>();

    private static class RoomItemCache {
        private final Map<Position2, SortedSet<RoomItem>> positionsToItems = new ConcurrentHashMap<>();
    }

    private static class RoomItemHeightComparator implements Comparator<RoomItem> {

        @Override
        public int compare(RoomItem o1, RoomItem o2) {
            float height1 = o1.getPosition().getZ();
            float height2 = o2.getPosition().getZ();

            return Float.compare(height1, height2);
        }
    }

    public void addItem(int roomId, RoomItem item) {
        roomsCache.putIfAbsent(roomId, new RoomItemCache());
        RoomItemCache roomCache = roomsCache.get(roomId);

        Position2 position = item.getPosition().toPosition2();

        roomCache.positionsToItems.putIfAbsent(position, new ConcurrentSkipListSet<>(new RoomItemHeightComparator()));
        Set<RoomItem> itemsSet = roomCache.positionsToItems.get(position);

        itemsSet.add(item);
    }

    public void removeItem(int roomId, RoomItem item) {
        RoomItemCache roomCache = roomsCache.get(roomId);

        if (roomCache == null) return;

        Position2 position = item.getPosition().toPosition2();

        SortedSet<RoomItem> itemsSet = roomCache.positionsToItems.get(position);

        itemsSet.remove(item);
    }

    public Set<RoomItem> getItemsAt(int roomId, Position2 position) {
        RoomItemCache roomCache = roomsCache.get(roomId);

        if (roomCache == null) return Collections.emptySet();

        return roomCache.positionsToItems.get(position);
    }

    public RoomItem getHighestItemAt(int roomId, Position2 position) {
        RoomItemCache roomCache = roomsCache.get(roomId);

        if (roomCache == null) return null;

        SortedSet<RoomItem> itemSet = roomCache.positionsToItems.get(position);

        if (itemSet == null) return null;

        return itemSet.last();
    }
}
