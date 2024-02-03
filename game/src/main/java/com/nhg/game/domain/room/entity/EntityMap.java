package com.nhg.game.domain.room.entity;

import com.nhg.game.domain.item.RoomItem;
import com.nhg.game.domain.room.entity.component.ComponentType;
import com.nhg.game.domain.room.entity.component.ItemComponent;
import com.nhg.game.domain.room.entity.component.UserComponent;
import com.nhg.game.domain.user.User;
import lombok.NonNull;

import java.util.Iterator;
import java.util.Map;
import java.util.Spliterator;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class EntityMap implements Iterable<Entity> {

    private final Map<UUID, Entity> entities = new ConcurrentHashMap<>();
    private final Map<Integer, User> users = new ConcurrentHashMap<>();;
    private final Map<Integer, RoomItem> items = new ConcurrentHashMap<>();

    public void add(@NonNull Entity entity) {
        entities.put(entity.getId(), entity);

        if (entity.hasComponent(ComponentType.User)) {
            User user = ((UserComponent) entity.getComponent(ComponentType.User)).getUser();;

            users.put(user.getId(), user);
        } else if (entity.hasComponent(ComponentType.Item)) {
            RoomItem item = ((ItemComponent) entity.getComponent(ComponentType.Item)).getItem();

            items.put(item.getId(), item);
        }
    }

    public void remove(@NonNull Entity entity) {
        entity = entities.remove(entity.getId());

        if (entity == null) return;

        if (entity.hasComponent(ComponentType.User)) {
            User user = ((UserComponent) entity.getComponent(ComponentType.User)).getUser();;

            users.put(user.getId(), user);
        } else if (entity.hasComponent(ComponentType.Item)) {
            RoomItem item = ((ItemComponent) entity.getComponent(ComponentType.Item)).getItem();

            items.put(item.getId(), item);
        }
    }

    public Entity getEntity(UUID entityId) {
        return entities.get(entityId);
    }

    public User getUser(int userId) {
        return users.get(userId);
    }

    public RoomItem getItem(int itemId) {
        return items.get(itemId);
    }

    public boolean hasUsers() {
        return !users.isEmpty();
    }

    @Override
    @NonNull
    public Iterator<Entity> iterator() {
        return entities.values().iterator();
    }

    @Override
    public void forEach(Consumer<? super Entity> action) {
        entities.values().forEach(action);
    }

    @Override
    public Spliterator<Entity> spliterator() {
        return entities.values().spliterator();
    }
}
