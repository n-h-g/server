package com.nhg.account.application.dto;

import java.util.Date;

public record TokenDataDto(
        Boolean isValid,
        String username,
        Date expiration
) { }
