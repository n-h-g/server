package com.cubs3d.account.dto;

import java.util.Date;

public record TokenDataResponse(
        Boolean isValid,
        String username,
        Date expiration
) { }
