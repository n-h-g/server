package com.nhg.game.infrastructure.networking.websocket;

import com.nhg.game.adapter.in.websocket.packet.IncomingPacket;
import com.nhg.game.infrastructure.networking.Client;
import com.nhg.game.infrastructure.networking.ClientRepository;
import com.nhg.game.infrastructure.networking.packet.PacketHandler;
import com.nhg.game.infrastructure.networking.packet.PacketHandlerIntJson;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@Component
@AllArgsConstructor
public class WebSocketHandler extends TextWebSocketHandler {

    private final ClientRepository<String> clients;

    private final PacketHandler<Integer, JSONObject> packetHandler =
            new PacketHandlerIntJson(IncomingPacket.HeaderClassMap);

    @Override
    public void handleTextMessage(@NonNull WebSocketSession session, @NonNull TextMessage message) {

        Client<String> client = clients.get(session.getId());

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
        Client<String> client = new WebSocketClient(session);
        clients.add(client);
        log.debug("Client "+ session.getId() +" connected");
    }

    @Override
    public void afterConnectionClosed(@NonNull WebSocketSession session, @NonNull CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);

        clients.remove(session.getId());
        log.debug("Client "+ session.getId() +" disconnected");
    }

}