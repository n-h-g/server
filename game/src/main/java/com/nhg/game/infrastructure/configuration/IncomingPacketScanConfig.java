package com.nhg.game.infrastructure.configuration;

import com.nhg.game.adapter.in.websocket.IncomingPacket;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        basePackages = "com.nhg.game.adapter.in.websocket.packet",
        includeFilters = @ComponentScan.Filter(
                type = FilterType.ANNOTATION, value = IncomingPacket.class
        )
)
public class IncomingPacketScanConfig { }
