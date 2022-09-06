package com.nhg.game.networking.message.outgoing;

import org.json.JSONException;
import org.json.JSONObject;

public interface JsonSerializable {

    /**
     * Convert the class to <code>JSONObject</code>.
     *
     * @return the JSONObject representing the class.
     * @throws JSONException indicate a problem with the JSON API.
     */
    JSONObject toJson() throws JSONException;
}
