package com.nhg.account.adapter.out.jpa.entity;

import com.nhg.account.domain.Account;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "account")
public class AccountJpa {

    @Id
    @GeneratedValue(
            generator = "sequence_account_id",
            strategy = GenerationType.SEQUENCE
    )
    @SequenceGenerator(
            name = "sequence_account_id",
            sequenceName = "sequence_account_id"
    )
    private Integer id;

    @Column(nullable = false, unique = true)
    private String username;

    private String email;

    @Column(nullable = false)
    private String password;

    public Account toAccount() {
        return Account.builder()
                .id(getId())
                .username(getUsername())
                .email(getEmail())
                .password(getPassword())
                .build();
    }

}