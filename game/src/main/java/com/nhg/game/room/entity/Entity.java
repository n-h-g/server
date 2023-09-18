package com.nhg.game.room.entity;

import com.nhg.game.item.Item;
import com.nhg.game.item.ItemType;
import com.nhg.game.item.interaction.Interaction;
import com.nhg.game.networking.message.outgoing.JsonSerializable;
import com.nhg.game.npc.behaviour.Behaviour;
import com.nhg.game.npc.bot.Bot;
import com.nhg.game.room.Room;
import com.nhg.game.room.entity.component.Component;
import com.nhg.game.room.entity.component.ComponentType;
import com.nhg.game.shared.HumanData;
import com.nhg.game.user.User;
import com.nhg.game.utils.Gender;
import com.nhg.game.utils.Position3;
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
 * Represents an entity in a room.
 *
 * @see Room
 */
@Slf4j
public class Entity implements JsonSerializable  {

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
            log.error("Error while adding component "+ type + " to entity with id: "+ id);
            e.printStackTrace();
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

    @Override
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

    /**
     * Create an entity from HumanData.
     * 
     * @param humanData the data used to create the entity.
     * @param room the room where the entity is created.
     * @return the created entity.
     * 
     * @see HumanData
     */
    public static Entity fromHumanData(@NonNull HumanData humanData, @NonNull Room room) {
        return new Entity(EntityType.HUMAN, room)
                .addComponent(ComponentType.Position, Pair.of(room.getRoomLayout().getDoorPosition3(), Position3.class))
                .addComponent(ComponentType.BodyHeadRotation, Pair.of(room.getRoomLayout().getDoorRotation(), Rotation.class))
                .addComponent(ComponentType.Action)
                .addComponent(ComponentType.Movement)
                .addComponent(ComponentType.HumanAspect, Pair.of(humanData.getLook(), String.class),
                        Pair.of(humanData.getGender(), Gender.class));
    }

    /**
     * Create an entity from User.
     * 
     * @param user the user used to create the entity.
     * @param room the room where the entity is created.
     * @return the created entity.
     *
     * @see User
     * @see #fromHumanData 
     */
    public static Entity fromUser(@NonNull User user, @NonNull Room room) {
        return fromHumanData(user.getHumanData(), room)
                .addComponent(ComponentType.User, Pair.of(user, User.class))
                .addComponent(ComponentType.Name, Pair.of(user.getUsername(), String.class));

    }

    /**
     * Create an entity from Bot.
     *
     * @param bot the bot used to create the entity.
     * @param room the room where the entity is created.
     * @return the created entity.
     *
     * @see Bot
     * @see #fromHumanData
     */
    public static Entity fromBot(@NonNull Bot bot, @NonNull Room room) {
        return fromHumanData(bot.getHumanData(), room)
                .addComponent(ComponentType.Name, Pair.of(bot.getName(), String.class))
                .addComponent(ComponentType.Behaviour, Pair.of(Behaviour.fromBot(bot), Behaviour.class));
    }

    /**
     * Create an entity from Item.
     *
     * @param item the item used to create the entity.
     * @param room the room where the entity is created.
     * @return the created entity.
     *
     * @see Item
     */
    public static Entity fromItem(@NonNull Item item, @NonNull Room room) {
        Entity entity = new Entity(EntityType.ITEM, room)
                .addComponent(ComponentType.Name, Pair.of(item.getItemSpecification().getName(), String.class))
                .addComponent(ComponentType.Item, Pair.of(item, Item.class))
                .addComponent(ComponentType.Interaction, Pair.of(Interaction.fromItem(item), Interaction.class));

        if (item.getItemSpecification().getItemType() == ItemType.FLOOR_ITEM) {
            entity
                .addComponent(ComponentType.Position, Pair.of(item.getPosition().getPosition3(), Position3.class))
                .addComponent(ComponentType.Rotation, Pair.of(item.getRotation(), Rotation.class));

        }

        return entity;
    }

}
