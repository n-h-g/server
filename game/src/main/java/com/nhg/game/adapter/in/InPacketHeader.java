package com.nhg.game.adapter.in;

public final class InPacketHeader {
    private InPacketHeader() {}

    public static final int Handshake = 1;
    public static final int Ping = 4;
    public static final int AllRooms = 6;
    public static final int MyRooms = 7;
    public static final int MoveItem = 26;
    public static final int PickUpItem = 27;
    public static final int PlaceItem = 25;
    public static final int RotateItem = 28;
    public static final int UseItem = 29;
    public static final int UserEnterRoom = 8;
    public static final int UserExitRoom = 9;
    public static final int UserMove = 10;
    public static final int CreateRoom = 36;
    public static final int InventoryItems = 14;
    public static final int UpdateUser = 15;
}
