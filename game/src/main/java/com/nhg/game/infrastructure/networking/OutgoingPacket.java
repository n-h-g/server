package com.nhg.game.infrastructure.networking;

import com.fasterxml.jackson.databind.JsonSerializable;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@Getter
@Setter
@Slf4j
public class OutgoingPacket implements Packet<Integer, JSONObject> {

    protected Integer header;
    protected JSONObject body;
    protected Client<?> client;

    @Override
    public String toString() {
        return "{\"header\": " + this.getHeader() + ", \"body\": " + this.body.toString() + "}";
    }

    public OutgoingPacket(int header) {
        this.header = header;
        this.body = new JSONObject();
    }

    public OutgoingPacket(int header, String data) {
        this(header);

        try {
            this.body.put("data", data);
        } catch (JSONException e) {
            log.error("Error creating packet with id: " + header);
        }

    }

    public OutgoingPacket(int header, boolean data) {
        this(header);

        try {
            this.body.put("data", data);
        } catch (JSONException e) {
            log.error("Error creating packet with id: " + header);
        }

    }

    public OutgoingPacket(int header, int data) {
        this(header);

        try {
            this.body.put("data", data);
        } catch (JSONException e) {
            log.error("Error creating packet with id: " + header);
        }

    }
}
