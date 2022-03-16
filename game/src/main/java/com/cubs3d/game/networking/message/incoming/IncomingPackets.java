package com.cubs3d.game.networking.message.incoming;

import com.cubs3d.game.networking.message.incoming.clientpackets.*;

public final class IncomingPackets {
    private IncomingPackets() {}

    public record Pair(Integer packetHeader, Class<? extends ClientPacket> packetClass) {};

    public static final Pair[] HeaderAndClassPairs = new Pair[] {
            new Pair(0, TestToken.class),
            new Pair(1, Handshake.class),
    };

}
