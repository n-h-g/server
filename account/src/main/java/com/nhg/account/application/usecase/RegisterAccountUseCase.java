package com.nhg.account.application.usecase;

import com.nhg.account.application.dto.RegisterAccountDto;
import com.nhg.account.application.exception.ProblemCode;
import com.nhg.account.application.exception.UseCaseException;
import com.nhg.account.application.repository.AccountRepository;
import com.nhg.account.domain.Account;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterAccountUseCase {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public void registerNewAccount(@Valid RegisterAccountDto accountDto) {
        accountRepository.findByUsername(accountDto.getUsername()).ifPresent(account -> {
            throw new UseCaseException(ProblemCode.ACCOUNT_ALREADY_EXIST);
        });

        Account account = Account.builder()
                .username(accountDto.getUsername())
                .email(accountDto.getEmail())
                .password(getEncryptedPassword(accountDto))
                .build();

        accountRepository.register(account);
    }

    private String getEncryptedPassword(RegisterAccountDto accountDto) {
        return passwordEncoder.encode(accountDto.getPassword());
    }
}
