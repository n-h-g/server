package com.cubs3d.account;

public record AccountRegistrationRequest(
        String username,
        String email,
        String password
) {
}
