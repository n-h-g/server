package com.nhg.game.dto;

import java.util.Date;

public record TokenDataResponse(
        Boolean isValid,
        String username,
        Date expiration
) { }
