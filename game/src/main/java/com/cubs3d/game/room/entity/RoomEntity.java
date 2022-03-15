package com.cubs3d.game.room.entity;

import com.cubs3d.game.room.Room;
import com.cubs3d.game.room.entity.component.Component;
import com.cubs3d.game.room.entity.component.ComponentType;

import java.util.EnumMap;
import java.util.Map;

public abstract class RoomEntity {

    private final Map<ComponentType, Component> components = new EnumMap<>(ComponentType.class);
    private final Room room;

    protected RoomEntity(Room room) {
        this.room = room;
    }

    public void cycle() {}

    protected void addComponent(ComponentType type) {
        try {
            Component component = type.getComponentClass()
                    .getDeclaredConstructor(ComponentType.class)
                    .newInstance(type);

            components.putIfAbsent(type, component);
        } catch (Exception e) {}
    }

    protected Component getComponent(ComponentType type) {
        if (!components.containsKey(type)) throw new IllegalArgumentException("Component not found for entity");
        return components.get(type);
    }

    @SuppressWarnings("unchecked")
    protected <T extends Component> T getComponent(ComponentType type, Class<T> clazz) {
        Component component = components.get(type);

        if (clazz.isInstance(component)) {
            return (T) component;
        }

        throw new IllegalArgumentException("Component not found for entity");

    }

}
