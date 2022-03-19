package com.cubs3d.game.networking.message.outgoing;

public final class OutgoingPacketHeaders {
    private OutgoingPacketHeaders() {}

    public static final int TestTokenResponse = 0;
    public static final int LoginMessageCheck = 1;
    public static final int PongResponse = 2;

    public static final int SendAllRooms = 8;
    public static final int SendMyRooms = 9;

    public static final int SendRoomData = 10;
    public static final int LoadRoomEntities = 11;
    public static final int UpdateEntity = 12;
    public static final int AddRoomEntity = 13;
    public static final int RemoveRoomEntity = 14;
}
