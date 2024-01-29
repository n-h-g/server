package com.nhg.account.adapter.in.rest;

import com.nhg.account.infrastructure.security.jwt.dto.TokenDataDto;
import com.nhg.account.infrastructure.security.jwt.dto.TokenDto;
import com.nhg.account.infrastructure.security.jwt.dto.TokenValidDto;
import com.nhg.account.infrastructure.security.jwt.JwtService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    private final JwtService jwtUseCase;

    @GetMapping("/generate_token/{username}")
    public TokenDto generateToken(@PathVariable String username) {
        return new TokenDto(jwtUseCase.generateToken(username));
    }

    @GetMapping("/token_valid/{token}")
    public TokenValidDto isTokenValid(@PathVariable("token") String token) {
        return new TokenValidDto(jwtUseCase.isTokenValid(token));
    }

    @GetMapping("/token_data/{token}")
    public TokenDataDto getTokenData(@PathVariable("token") String token) {
        Boolean isValid = jwtUseCase.isTokenValid(token);
        String username = jwtUseCase.getUsernameFromToken(token);
        Date expiration = jwtUseCase.getExpirationFromToken(token);

        return new TokenDataDto(
                isValid,
                username,
                expiration
        );
    }
}