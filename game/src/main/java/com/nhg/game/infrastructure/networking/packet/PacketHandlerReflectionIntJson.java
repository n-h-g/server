package com.nhg.game.infrastructure.networking.packet;


import com.nhg.game.adapter.in.websocket.packet.IncomingPacket;
import com.nhg.game.infrastructure.networking.Client;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class PacketHandlerReflectionIntJson implements PacketHandler<Integer, JSONObject> {

    private final Map<Integer, Class<? extends ClientPacket<Integer, JSONObject>>> clientPackets =
            IncomingPacket.HeaderClassMap;


    @Override
    public void handle(@NonNull Client<?> client, @NonNull Integer header, JSONObject body) {
        try {
            final ClientPacket<Integer, JSONObject> packet = this.clientPackets.get(header)
                    .getDeclaredConstructor().newInstance();

            packet.setHeader(header);
            packet.setBody(body);
            packet.setClient(client);
            packet.handle();

        } catch (Exception e) {
            log.error("Error in packet handling: " + e);
        }
    }
}
