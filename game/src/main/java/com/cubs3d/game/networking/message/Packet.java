package com.cubs3d.game.networking.message;


public interface Packet<Header, Body> {

    Header getHeader();
    void setHeader(Header header);

    Body getBody();
    void setBody(Body header);



}
