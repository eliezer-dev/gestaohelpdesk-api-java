package dev.eliezer.superticket.service;

import dev.eliezer.superticket.domain.model.Ticket;
import dev.eliezer.superticket.dto.TicketCountResponseDTO;
import dev.eliezer.superticket.dto.TicketRequestDTO;
import dev.eliezer.superticket.dto.TicketResponseDTO;
import dev.eliezer.superticket.dto.TicketResponseForIndexDTO;

import java.util.List;

public interface TicketService {
    List<TicketResponseDTO> index(Long userId, Long type, String search, Long searchType);

    TicketCountResponseDTO getTicketsCount(Long userId);

    TicketResponseDTO findById(Long id);

    TicketResponseDTO insert(TicketRequestDTO ticketRequestDTO);

    TicketResponseDTO update(Long id, TicketRequestDTO ticketRequestDTO);

    void delete (Long id);
}
