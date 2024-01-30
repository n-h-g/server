package com.nhg.game.infrastructure.networking;

public interface ClientRepository<ClientId> {

    void add(Client<ClientId> client);
    void remove(ClientId clientId);
    Client<ClientId> get(ClientId clientId);
}
