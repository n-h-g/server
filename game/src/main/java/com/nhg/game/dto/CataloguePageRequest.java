package com.nhg.game.dto;


import com.nhg.game.networking.message.outgoing.JsonSerializable;
import org.json.JSONException;
import org.json.JSONObject;

public record CataloguePageRequest(
        Integer id,
        String title,
        String description,
        int sequence

) implements JsonSerializable {
    @Override
    public JSONObject toJson() throws JSONException {
        return new JSONObject()
                .put("id", id)
                .put("title", title)
                .put("description", description)
                .put("sequence", sequence);
    }
}
