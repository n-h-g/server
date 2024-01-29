package com.nhg.account.application.exception;

import lombok.Getter;

@Getter
public enum ProblemCode {
    ACCOUNT_ALREADY_EXIST("account_already_exist");


    private final String code;

    ProblemCode(String code) {
        this.code = code;
    }
}
