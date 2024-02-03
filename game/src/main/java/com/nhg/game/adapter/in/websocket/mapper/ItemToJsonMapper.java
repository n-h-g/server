package com.nhg.game.adapter.in.websocket.mapper;

import com.nhg.game.domain.item.Item;
import lombok.NonNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class ItemToJsonMapper {

    public JSONObject itemToJson(@NonNull Item item) {
        return new JSONObject()
                .put("id", item.getId())
                .put("name", item.getPrototype().getName());
    }

    public JSONObject itemsToJson(@NonNull Iterable<Item> items) {
        JSONObject json = new JSONObject();
        JSONArray data = new JSONArray();
        try {
            for (Item item : items) {
                data.put(itemToJson(item));
            }

            json.put("data", data);
        } catch (JSONException ignored) {}

        return json;
    }
}
