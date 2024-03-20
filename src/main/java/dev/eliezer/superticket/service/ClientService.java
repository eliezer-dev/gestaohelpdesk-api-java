package dev.eliezer.superticket.service;

import dev.eliezer.superticket.domain.model.Client;

public interface ClientService {

    Iterable<Client> index(String search);

    Client findById(Long id);

    Client insert(Client client);

    Client update(Long id, Client client);

    void delete (Long id);
}
