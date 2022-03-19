package com.cubs3d.account.service;

import com.cubs3d.account.model.Account;
import com.cubs3d.account.dto.AccountRegistrationRequest;
import com.cubs3d.account.repository.AccountRepository;
import com.cubs3d.account.service.security.JwtService;
import lombok.AllArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    public void registerAccount(AccountRegistrationRequest request) {
        Account account = Account.builder()
                .username(request.username())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .build();

        //TODO: validation checks

        accountRepository.saveAndFlush(account);
    }
}
