package com.nhg.account.controller;

import com.nhg.account.dto.AccountRegistrationRequest;
import com.nhg.account.dto.TokenDataResponse;
import com.nhg.account.dto.TokenResponse;
import com.nhg.account.dto.TokenValidResponse;
import com.nhg.account.service.AccountService;
import com.nhg.account.service.security.JwtService;
import lombok.AllArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("api/v1/accounts")
public class AccountController {

    private final AccountService accountService;
    private final JwtService jwtService;

    @PostMapping("/register")
    public void registerAccount(@RequestBody AccountRegistrationRequest request) {
        accountService.registerAccount(request);
    }

    @GetMapping("/generate_token/{username}")
    public TokenResponse generateToken(@PathVariable String username) {
        return new TokenResponse(jwtService.generateToken(username));
    }

    @GetMapping("/token_valid/{token}")
    public TokenValidResponse isTokenValid(@PathVariable("token") String token) {
        return new TokenValidResponse(jwtService.isTokenValid(token));
    }

    @GetMapping("/token_data/{token}")
    public TokenDataResponse getTokenData(@PathVariable("token") String token) {
        Boolean isValid = jwtService.isTokenValid(token);
        String username = jwtService.getUsernameFromToken(token);
        Date expiration = jwtService.getExpirationFromToken(token);

        return new TokenDataResponse(
                isValid,
                username,
                expiration
        );
    }
}