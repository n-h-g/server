package com.cubs3d.account.dto;

public record AccountRegistrationRequest(
        String username,
        String email,
        String password
) {
}
