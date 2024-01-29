package com.nhg.account.adapter.out.jpa;


import com.nhg.account.adapter.out.jpa.entity.AccountJpa;
import com.nhg.account.adapter.out.jpa.repository.AccountJpaRepository;
import com.nhg.account.adapter.out.jpa.service.RegisterNewAccountJpaService;
import com.nhg.account.application.repository.AccountRepository;
import com.nhg.account.domain.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AccountJpaRepositoryAdapter implements AccountRepository {

    private final RegisterNewAccountJpaService registerNewAccountJpaService;
    private final AccountJpaRepository accountJpaRepository;

    @Override
    @Transactional
    public void register(Account account) {
        registerNewAccountJpaService.register(account);
    }

    @Override
    @Transactional
    public Optional<Account> findByUsername(String username) {
        return accountJpaRepository.findByUsername(username)
                .map(AccountJpa::toAccount);
    }
}
