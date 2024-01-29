package com.nhg.account.adapter.out.jpa.repository;


import com.nhg.account.adapter.out.jpa.entity.AccountJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AccountJpaRepository extends JpaRepository<AccountJpa, Integer> {

    Optional<AccountJpa> findByUsername(String username);
}

