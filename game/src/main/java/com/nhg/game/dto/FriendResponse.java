package com.nhg.game.dto;

import com.nhg.game.networking.message.outgoing.JsonSerializable;
import com.nhg.game.user.User;
import org.json.JSONException;
import org.json.JSONObject;

public record FriendResponse(
        User user,
        Integer friendshipId,
        Integer senderId,
        Integer destinationId,
        boolean pending
) implements JsonSerializable {
    @Override
    public JSONObject toJson() throws JSONException {
        return user.toJson().put("friend", new JSONObject()
                .put("friendshipId", friendshipId)
                .put("senderId", senderId)
                .put("destinationId", destinationId)
                .put("pending", pending));
    }
}
