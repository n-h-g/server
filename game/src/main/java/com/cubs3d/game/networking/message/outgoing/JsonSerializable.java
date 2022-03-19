package com.cubs3d.game.networking.message.outgoing;

import org.json.JSONException;
import org.json.JSONObject;

public interface JsonSerializable {

    JSONObject toJson() throws JSONException;
}
