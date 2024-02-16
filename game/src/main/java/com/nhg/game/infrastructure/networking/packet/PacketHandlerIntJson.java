package com.nhg.game.infrastructure.networking.packet;

import com.nhg.game.adapter.in.websocket.IncomingPacket;
import com.nhg.game.infrastructure.context.ApplicationContextUtils;
import com.nhg.game.infrastructure.networking.Client;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class PacketHandlerIntJson implements PacketHandler<Integer, JSONObject> {

    private final Map<Integer, ClientPacket<JSONObject>> clientPackets = getBeanPackets();

    @Override
    public void handle(@NonNull Client<?> client, @NonNull Integer header, JSONObject body) {
        try {
            final ClientPacket<JSONObject> packet = this.clientPackets.get(header);

            packet.handle(client, body);

        } catch (Exception e) {
            log.error("Error in packet handling: " + e);
        }
    }

    @SuppressWarnings("unchecked")
    private static Map<Integer, ClientPacket<JSONObject>> getBeanPackets() {
        Map<Integer, ClientPacket<JSONObject>> cp = new HashMap<>();

        ApplicationContextUtils.getApplicationContext().getBeansWithAnnotation(IncomingPacket.class)
                .forEach((beanName, bean) -> {
                    IncomingPacket annotation = bean.getClass().getAnnotation(IncomingPacket.class);
                    int header = annotation.header();

                    cp.put(header, (ClientPacket<JSONObject>) bean);
                });

        return cp;
    }
}
