package dev.eliezer.superticket.service;

import dev.eliezer.superticket.domain.model.Ticket;
import dev.eliezer.superticket.dto.TicketResponseDTO;

public interface TicketService {
    Iterable<Ticket> findAll();
    Ticket findById(Long id);

    TicketResponseDTO insert(Ticket ticket);

    TicketResponseDTO update(Long id, Ticket ticket);

    void delete (Long id);
}
