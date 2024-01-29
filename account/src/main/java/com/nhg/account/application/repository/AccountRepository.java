package com.nhg.account.application.repository;

import com.nhg.account.domain.Account;

import java.util.Optional;

public interface AccountRepository {

    void register(Account account);

    Optional<Account> findByUsername(String username);
}
