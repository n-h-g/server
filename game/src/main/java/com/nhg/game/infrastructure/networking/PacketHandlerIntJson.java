package com.nhg.game.infrastructure.networking;


import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class PacketHandlerIntJson implements PacketHandler<Integer, JSONObject> {

    private final Map<Integer, Class<? extends ClientPacket<Integer, JSONObject>>> clientPackets;

    @Override
    public void handle(@NonNull Client client, @NonNull Integer header, JSONObject body) {
        try {
            final ClientPacket<Integer, JSONObject> packet = this.clientPackets.get(header).getDeclaredConstructor().newInstance();

            packet.setHeader(header);
            packet.setBody(body);
            packet.setClient(client);
            packet.handle();

        } catch (Exception e) {
            log.error("Error in packet handling: " + e);
        }
    }

    /**
     * Register the client packet, so it will be available for future handling.
     *
     * @param header packet header
     * @param packet packet class
     */
    private void registerClientPacket(@NonNull Integer header, @NonNull Class<? extends ClientPacket<Integer, JSONObject>> packet) {
        this.clientPackets.putIfAbsent(header, packet);
    }
}
