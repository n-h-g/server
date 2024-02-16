package com.nhg.game.infrastructure.networking.packet;

/**
 * Generic representation of a packet.
 *
 * @param <Header> Class for representing the packet header.
 * @param <Body> Class for representing the packet Body.
 */
public interface ServerPacket<Header, Body> {

    Header getHeader();
    void setHeader(Header header);

    Body getBody();
    void setBody(Body header);



}
