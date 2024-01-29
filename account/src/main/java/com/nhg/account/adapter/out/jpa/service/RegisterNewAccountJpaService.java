package com.nhg.account.adapter.out.jpa.service;

import com.nhg.account.adapter.out.jpa.entity.AccountJpa;
import com.nhg.account.adapter.out.jpa.repository.AccountJpaRepository;
import com.nhg.account.domain.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterNewAccountJpaService {

    private final AccountJpaRepository accountJpaRepository;

    public void register(Account account) {
        AccountJpa accountJpa = AccountJpa.builder()
                        .id(account.getId())
                        .username(account.getUsername())
                        .email(account.getEmail())
                        .password(account.getPassword())
                        .build();

        accountJpaRepository.saveAndFlush(accountJpa);
    }
}
