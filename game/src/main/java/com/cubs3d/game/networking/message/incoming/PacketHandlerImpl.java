package com.cubs3d.game.networking.message.incoming;

import com.cubs3d.game.networking.Client;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class PacketHandlerImpl implements PacketHandler<Integer, JSONObject> {


    private final Map<Integer, Class<? extends ClientPacket>> clientPackets;

    public PacketHandlerImpl() {
        clientPackets = new HashMap<>();

        this.registerPackets();
    }

    @Override
    public void handle(@NonNull Client client, @NonNull Integer header, JSONObject body) {
        try {
            final ClientPacket packet = this.clientPackets.get(header).getDeclaredConstructor().newInstance();

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
    private void registerClientPacket(@NonNull Integer header, @NonNull Class<? extends ClientPacket> packet) {
        this.clientPackets.putIfAbsent(header, packet);
    }

    /**
     * Register all the incoming packets
     * @see IncomingPackets
     */
    private void registerPackets() {
        for (var pair : IncomingPackets.HeaderAndClassPairs) {
            this.registerClientPacket(pair.packetHeader(), pair.packetClass());
        }

    }
}
