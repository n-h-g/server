package com.nhg.game.room.entity;

import com.nhg.game.networking.message.outgoing.JsonSerializable;
import com.nhg.game.room.Room;
import com.nhg.game.room.entity.component.Component;
import com.nhg.game.room.entity.component.ComponentType;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.data.util.Pair;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.UUID;

/**
 * Represent an entity in a room.
 *
 * @see Room
 */
@Slf4j
public abstract class Entity implements JsonSerializable  {

    @Getter
    private final UUID id;

    @Getter
    private final EntityType type;

    private final EnumMap<ComponentType, Component> components;

    @Getter
    private final Room room;

    public Entity(UUID id, EntityType type, @NonNull Room room) {
        this.id = id;
        this.type = type;
        this.room = room;
        this.components = new EnumMap<>(ComponentType.class);
    }

    /**
     * This function is called every room cycle.
     *
     * @see Room#run()
     */
    public void cycle() {
        for (Component component : components.values()) {
            component.cycle();
        }
    }

    @SafeVarargs
    public final Entity addComponent(ComponentType type, Pair<Object, Class<?>>... pairs) {
        Class<? extends Component> componentClass = type.getComponentClass();

        Object[] args = Arrays.stream(pairs).map(Pair::getFirst).toArray();
        Class<?>[] types = Arrays.stream(pairs).map(Pair::getSecond).toArray(Class<?>[]::new);

        try {
            Component component = componentClass.getDeclaredConstructor(types).newInstance(args);
            component.setEntity(this);
            components.put(type, component);
        } catch (Exception e) {
            log.error("Error while adding component "+ type + " to entity with id: "+ id);
            e.printStackTrace();
        }

        return this;
    }

    public Component getComponent(ComponentType type) {
        return components.get(type);
    }

    public JSONObject toJson() throws JSONException {

        JSONObject components = new JSONObject();
        this.components.forEach((type, component) -> {
            try {
                if (component.toJson() != null) {
                    components.put(type.getName(), component.toJson());
                }
            } catch (JSONException ignored) {}
        });

        return new JSONObject()
                .put("id", id)
                .put("type", type.getCode())
                .put("components", components);
    }

}
