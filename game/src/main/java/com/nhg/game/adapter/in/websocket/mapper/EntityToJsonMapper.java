package com.nhg.game.adapter.in.websocket.mapper;


import com.nhg.game.domain.room.entity.Entity;
import com.nhg.game.domain.room.entity.component.ActionComponent;
import com.nhg.game.domain.room.entity.component.BodyHeadRotationComponent;
import com.nhg.game.domain.room.entity.component.Component;
import com.nhg.game.domain.room.entity.component.HumanAspectComponent;
import com.nhg.game.domain.room.entity.component.NameComponent;
import com.nhg.game.domain.room.entity.component.PositionComponent;
import com.nhg.game.domain.room.entity.component.RotationComponent;
import com.nhg.game.domain.room.entity.component.UserComponent;
import lombok.NonNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@org.springframework.stereotype.Component
public class EntityToJsonMapper {

    public JSONObject entityToJson(@NonNull Entity entity) {
        JSONObject json = new JSONObject()
                .put("id", entity.getId())
                .put("type", entity.getType().getCode());

        entity.getComponents().forEach((type, component) -> {
            try {
                JSONObject componentJson = this.componentToJson(component);

                if (componentJson != null) {
                    json.put(type.getName(), componentJson);
                }

            } catch (JSONException ignored) {}
        });

        return json;
    }

    public JSONObject entitiesToJson(@NonNull Iterable<Entity> entities) {
        JSONObject json = new JSONObject();
        JSONArray data = new JSONArray();
        try {
            for (Entity entity : entities) {
                data.put(entityToJson(entity));
            }

            json.put("data", data);
        } catch (JSONException ignored) {}

        return json;
    }

    private JSONObject componentToJson(Component component) {
        switch (component) {
            case ActionComponent cmp -> {
                JSONArray actions = new JSONArray();
                cmp.getActions().forEach(action -> actions.put(action.getValue()));

                return new JSONObject().put("actions", actions);
            }

            case BodyHeadRotationComponent cmp -> {
                return new JSONObject()
                        .put("body_rot", cmp.getBodyRotation().getValue())
                        .put("head_rot", cmp.getHeadRotation().getValue());
            }

            case HumanAspectComponent cmp -> {
                return new JSONObject()
                        .put("look", cmp.getLook())
                        .put("gender", cmp.getGender());
            }

            case NameComponent cmp -> {
                return new JSONObject()
                        .put("name", cmp.getName());
            }

            case PositionComponent cmp -> {
                return new JSONObject()
                        .put("x", cmp.getPosition().getX())
                        .put("y", cmp.getPosition().getY())
                        .put("z", cmp.getPosition().getZ());
            }

            case RotationComponent cmp -> {
                return new JSONObject()
                        .put("rot", cmp.getRotation().getValue());
            }

            case UserComponent cmp -> {
                return new JSONObject()
                        .put("id", cmp.getUser().getId());
            }

            default -> { return null; }
        }
    }

}
