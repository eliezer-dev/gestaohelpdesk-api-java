package dev.eliezer.superticket.service;

import dev.eliezer.superticket.domain.model.Ticket;
import dev.eliezer.superticket.dto.TicketRequestDTO;
import dev.eliezer.superticket.dto.TicketResponseDTO;
import dev.eliezer.superticket.dto.TicketResponseForIndexDTO;

public interface TicketService {
    TicketResponseForIndexDTO index(Long userId);
    TicketResponseDTO findById(Long id);

    TicketResponseDTO insert(TicketRequestDTO ticketRequestDTO);

    TicketResponseDTO update(Long id, TicketRequestDTO ticketRequestDTO);

    void delete (Long id);
}
