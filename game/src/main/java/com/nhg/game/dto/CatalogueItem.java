package com.nhg.game.dto;

import com.nhg.game.networking.message.outgoing.JsonSerializable;
import org.json.JSONException;
import org.json.JSONObject;

public record CatalogueItem(
         Integer id,
         String name,
         int amount,
         int credits

) implements JsonSerializable {
    @Override
    public JSONObject toJson() throws JSONException {
        return new JSONObject()
                .put("id", id)
                .put("name", name)
                .put("amount", amount)
                .put("credits", credits);
    }
}

