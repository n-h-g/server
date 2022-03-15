package com.cubs3d.game.networking;

import com.cubs3d.game.networking.message.incoming.PacketHandler;
import com.cubs3d.game.networking.message.incoming.PacketHandlerImpl;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import org.json.JSONObject;


@Slf4j
@Component
@AllArgsConstructor
public class WebSocketHandler extends TextWebSocketHandler {

    private final Clients clients;

    private final PacketHandler<Integer, JSONObject> packetHandler = new PacketHandlerImpl();;

    @Override
    public void handleTextMessage(@NonNull WebSocketSession session, @NonNull TextMessage message) {

        Client client = clients.get(session.getId());

        if (client == null) return;

        log.debug("Message received: " + message.getPayload());

        try {
            JSONObject jsonObject = new JSONObject(message.getPayload());

            Integer packetHeader = jsonObject.getInt("header");
            JSONObject packetBody = jsonObject.getJSONObject("body");

            packetHandler.handle(client, packetHeader, packetBody);

        } catch(JSONException e) {
            log.error("Wrong Json Syntax: "+ e);
        }

    }

    @Override
    public void afterConnectionEstablished(@NonNull WebSocketSession session) {
        clients.add(session);
        log.debug("Client "+ session.getId() +" connected");
    }

    @Override
    public void afterConnectionClosed(@NonNull WebSocketSession session, @NonNull CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);

        clients.remove(session.getId());
        log.debug("Client "+ session.getId() +" disconnected");
    }

}
