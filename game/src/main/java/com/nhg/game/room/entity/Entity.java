package com.nhg.game.room.entity;

import com.nhg.game.item.Item;
import com.nhg.game.networking.message.outgoing.JsonSerializable;
import com.nhg.game.room.Room;
import com.nhg.game.room.entity.component.Component;
import com.nhg.game.room.entity.component.ComponentType;
import com.nhg.game.user.HumanData;
import com.nhg.game.user.User;
import com.nhg.game.utils.Gender;
import com.nhg.game.utils.Int3;
import com.nhg.game.utils.Rotation;
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
public class Entity implements JsonSerializable  {

    @Getter
    private final UUID id;

    @Getter
    private final EntityType type;

    private final EnumMap<ComponentType, Component> components;

    @Getter
    private final Room room;

    public Entity(EntityType type, @NonNull Room room) {
        this.id = UUID.randomUUID();
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

        JSONObject json = new JSONObject()
                .put("id", id)
                .put("type", type.getCode());

        this.components.forEach((type, component) -> {
            try {
                if (component.toJson() != null) {
                    json.put(type.getName(), component.toJson());
                }
            } catch (JSONException ignored) {}
        });

        return json;
    }

    public static Entity fromHumanData(@NonNull HumanData humanData, @NonNull Room room) {
        return new Entity(EntityType.HUMAN, room)
                .addComponent(ComponentType.Position, Pair.of(room.getRoomLayout().getInt3AtDoor(), Int3.class))
                .addComponent(ComponentType.BodyHeadRotation, Pair.of(room.getRoomLayout().getDoorRotation(), Rotation.class))
                .addComponent(ComponentType.Action)
                .addComponent(ComponentType.Movement)
                .addComponent(ComponentType.HumanAspect, Pair.of(humanData.getLook(), String.class),
                        Pair.of(humanData.getGender(), Gender.class));
    }

    public static Entity fromUser(@NonNull User user, @NonNull Room room) {
        return fromHumanData(user.getHumanData(), room)
                .addComponent(ComponentType.User, Pair.of(user, User.class))
                .addComponent(ComponentType.Name, Pair.of(user.getUsername(), String.class));

    }

    public static Entity fromItem(@NonNull Item item, @NonNull Room room) {
        return new Entity(EntityType.ITEM, room)
                .addComponent(ComponentType.Name, Pair.of(item.getItemSpecification().getName(), String.class))
                .addComponent(ComponentType.Position, Pair.of(item.getPosition(), Int3.class));
    }

}
