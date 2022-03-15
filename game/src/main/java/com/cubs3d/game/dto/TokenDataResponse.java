package com.cubs3d.game.dto;

import java.util.Date;

public record TokenDataResponse(
        Boolean isValid,
        String username,
        Date expiration
) { }
