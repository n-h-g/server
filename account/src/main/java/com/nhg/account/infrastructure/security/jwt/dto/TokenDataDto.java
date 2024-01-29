package com.nhg.account.infrastructure.security.jwt.dto;

import java.util.Date;

public record TokenDataDto(
        Boolean isValid,
        String username,
        Date expiration
) { }
