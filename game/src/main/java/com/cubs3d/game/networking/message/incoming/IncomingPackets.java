package com.cubs3d.game.networking.message.incoming;

import com.cubs3d.game.networking.message.incoming.clientpackets.*;
import com.cubs3d.game.networking.message.incoming.clientpackets.handshake.Handshake;
import com.cubs3d.game.networking.message.incoming.clientpackets.handshake.PingRequest;

public final class IncomingPackets {
    private IncomingPackets() {}

    public record Pair(Integer packetHeader, Class<? extends ClientPacket> packetClass) {};

    public static final Pair[] HeaderAndClassPairs = new Pair[] {
            new Pair(0, TestToken.class),
            new Pair(1, Handshake.class),
            new Pair(4, PingRequest.class)
    };

}
