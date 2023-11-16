package dev.eliezer.superticket.service;

import dev.eliezer.superticket.domain.model.Client;
import dev.eliezer.superticket.domain.model.Ticket;

public interface ClientService {
    Iterable<Client> findAll();
    Client findById(Long id);

    Client insert(Client client);

    Client update(Long id, Client client);

    void delete (Long id);
}
