package com.nhg.account.application.exception;

public class UseCaseException extends RuntimeException {

    public UseCaseException(ProblemCode problem) {
        super(problem.getCode());
    }
}
