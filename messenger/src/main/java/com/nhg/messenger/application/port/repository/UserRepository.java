package com.nhg.messenger.application.port.repository;

public interface UserRepository {

    boolean existsById(int userId);
}
