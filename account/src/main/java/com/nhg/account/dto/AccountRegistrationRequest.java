package com.nhg.account.dto;

public record AccountRegistrationRequest(
        String username,
        String email,
        String password
) {
}
