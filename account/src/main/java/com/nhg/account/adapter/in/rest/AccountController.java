package com.nhg.account.adapter.in.rest;

import com.nhg.account.application.dto.RegisterAccountDto;
import com.nhg.account.application.usecase.RegisterAccountUseCase;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("api/v1/accounts")
public class AccountController {

    private final RegisterAccountUseCase registerAccountUseCase;

    @PostMapping("/register")
    public void registerAccount(@RequestBody RegisterAccountDto request) {
        registerAccountUseCase.registerNewAccount(request);
    }

}