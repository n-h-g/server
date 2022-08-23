package com.cubs3d.game.networking.message.outgoing;

public final class OutgoingPacketHeaders {
    private OutgoingPacketHeaders() {}

    //# Handshake and generic
    public static final int LoginMessageCheck = 1;
    public static final int PongResponse = 2;

    //# Navigator
    public static final int SendAllRooms = 8;
    public static final int SendMyRooms = 9;

    //# Room
    public static final int SendRoomData = 10;
    public static final int LoadRoomEntities = 11;
    public static final int UpdateEntity = 12;
    public static final int AddRoomEntity = 13;
    public static final int RemoveRoomEntity = 14;
    public static final int RoomChatMessage = 15;
    public static final int RoomUserType = 16;

    //# User
    public static final int UpdateUserInformation = 17;

    //# Friends
    public static final int FriendsList = 20;
    public static final int UpdateFriendStatus = 21;

    //# Items
    public static final int LoadItems = 18;
    public static final int LoadRoomItems = 23;

    //# Generic
    public static final int BubbleAlert = 101;
    public static final int DisconnectClient = 102;
}
