package dev.eliezer.superticket.service;

import dev.eliezer.superticket.domain.model.Ticket;

public interface TicketService {
    Iterable<Ticket> findAll();
    Ticket findById(Long id);

    Ticket insert(Ticket ticket);

    Ticket update(Long id, Ticket ticket);

    void delete (Long id);
}
