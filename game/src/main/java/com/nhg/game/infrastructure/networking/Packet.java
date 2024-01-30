package com.nhg.game.infrastructure.networking;

/**
 * Generic representation of a packet.
 *
 * @param <Header> Class for representing the packet header.
 * @param <Body> Class for representing the packet Body.
 */
public interface Packet<Header, Body> {

    Header getHeader();
    void setHeader(Header header);

    Body getBody();
    void setBody(Body header);



}
