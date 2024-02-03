package com.nhg.game.adapter.in.websocket.mapper;

import com.nhg.game.domain.user.User;
import lombok.NonNull;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class UserToJsonMapper {

    public JSONObject userToJson(@NonNull User user) {
        return new JSONObject()
                .put("id", user.getId())
                .put("username", user.getUsername())
                .put("motto", user.getMotto())
                .put("gender", user.getGender().toChar())
                .put("look", user.getLook())
                .put("credits", user.getCredits());

    }
}