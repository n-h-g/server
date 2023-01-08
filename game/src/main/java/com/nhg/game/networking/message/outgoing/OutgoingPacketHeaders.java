package com.nhg.game.networking.message.outgoing;

public final class OutgoingPacketHeaders {
    private OutgoingPacketHeaders() {
    }

    //region# Handshake and generic (0 to 99)
    public static final int LoginMessageCheck = 1;
    public static final int PongResponse = 2;
    public static final int DisconnectClient = 3;
    public static final int HotelView = 4;
    public static final int BubbleAlert = 5;
    //endregion

    //region# Navigator (100 to 199)
    public static final int SendAllRooms = 101;
    public static final int SendMyRooms = 102;
    //endregion

    //region# Room (200 to 399)
    public static final int SendRoomData = 200;
    public static final int RoomCreationResponse = 201;
    public static final int LoadRoomEntities = 202;
    public static final int UpdateEntity = 203;
    public static final int AddRoomEntity = 204;
    public static final int RemoveRoomEntity = 205;
    public static final int RoomChatMessage = 206;
    public static final int RoomUserType = 207;
    //endregion

    //region# User (400 to 599)
    public static final int UpdateUserInformation = 400;
    public static final int InventoryItems = 401;
    //endregion

    //region# Items (600 to 799)
    //endregion

    //region# Friends (800 to 899)
    //endregion
}
