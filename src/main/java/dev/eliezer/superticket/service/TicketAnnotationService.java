package dev.eliezer.superticket.service;

import dev.eliezer.superticket.dto.*;

import java.util.List;

public interface TicketAnnotationService {
    List<TicketAnnotationResponseDTO> findByTicketId(Long ticketId);
    TicketAnnotationResponseDTO findById(Long id);

    TicketAnnotationResponseDTO insert(TicketAnnotationRequestDTO request);

    void delete (Long id);
}
