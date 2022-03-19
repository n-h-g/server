
package com.cubs3d.game.networking.message.outgoing;

import com.cubs3d.game.networking.message.WebSocketJsonPacket;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Slf4j
public class ServerPacket extends WebSocketJsonPacket {

    public ServerPacket(int header) {
        this.header = header;
        this.body = new JSONObject();
    }

    public ServerPacket(int header, JSONObject jsonObject) {
        this(header);
        this.body = jsonObject;
    }

    public ServerPacket(int header, String data) {
        this(header);

        try {
            this.body.put("data", data);
        } catch (JSONException e) {
            log.error("Error creating packet with id: "+ header);
        }

    }
    public ServerPacket(int header, boolean data) {
        this(header);

        try {
            this.body.put("data", data);
        } catch (JSONException e) {
            log.error("Error creating packet with id: "+ header);
        }

    }
    public ServerPacket(int header, int data) {
        this(header);

        try {
            this.body.put("data", data);
        } catch (JSONException e) {
            log.error("Error creating packet with id: "+ header);
        }

    }

    public ServerPacket(int header, JsonSerializable jsonSerializable) {
        this(header);

        try {
            this.body = jsonSerializable.toJson();
        } catch (JSONException e) {
            log.error("Error creating packet with id: "+ header);
        }
    }

    public ServerPacket(int header, List<JsonSerializable> col) {
        this(header);

        JSONArray data = new JSONArray();
        try {
            for (JsonSerializable jsonSerializable : col) {
                data.put(jsonSerializable.toJson());
            }

            this.body.put("data", data);

        } catch (JSONException e) {
            log.error("Error creating packet with id: "+ header);
        }

    }
}
