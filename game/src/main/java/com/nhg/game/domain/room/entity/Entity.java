package com.nhg.game.domain.room.entity;


import com.nhg.game.domain.room.Room;
import com.nhg.game.domain.room.entity.component.Component;
import com.nhg.game.domain.room.entity.component.ComponentType;
import com.nhg.game.domain.shared.human.Gender;
import com.nhg.game.domain.shared.position.Position3;
import com.nhg.game.domain.shared.position.Rotation;
import com.nhg.game.domain.user.User;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.UUID;

/**
 * Represents an entity in a room.
 *
 * @see Room
 */
@Slf4j
public class Entity {

    @Getter
    private final UUID id;

    @Getter
    private final EntityType type;

    @Getter
    private final Room room;

    private final EnumMap<ComponentType, Component> components;

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

    /**
     * Add a component with the given type to the entity.
     *
     * @param type the type of the component.
     * @param pairs list of pair (object-value, object-class) representing the component constructor parameters.
     * @return the entity where the component is added (used for multiple operations on the entity).
     *
     * @see ComponentType
     * @see Component
     */
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
            log.error(String.format("Error while adding component %s to entity with id %s, error: %s",
                    type, id, e.getMessage()));
        }

        return this;
    }

    /**
     * Get the component associated to the entity with the given type.
     *
     * @param type the type of the component.
     * @return the requested component.
     *
     * @see ComponentType
     * @see Component
     */
    public Component getComponent(ComponentType type) {
        return components.get(type);
    }

    /**
     * Create an entity from User.
     *
     * @param user the user used to create the entity.
     * @param room the room where the entity is created.
     * @return the created entity.
     *
     * @see User
     */
    public static Entity fromUser(@NonNull User user, @NonNull Room room) {
        return new Entity(EntityType.HUMAN, room)
                .addComponent(ComponentType.Position, Pair.of(room.getRoomLayout().getDoorPosition3(), Position3.class))
                .addComponent(ComponentType.BodyHeadRotation, Pair.of(room.getRoomLayout().getDoorRotation(), Rotation.class))
                .addComponent(ComponentType.Action)
                .addComponent(ComponentType.Movement)
                .addComponent(ComponentType.HumanAspect, Pair.of(user.getLook(), String.class),
                        Pair.of(user.getGender(), Gender.class))
                .addComponent(ComponentType.User, Pair.of(user, User.class))
                .addComponent(ComponentType.Name, Pair.of(user.getUsername(), String.class));

    }

}