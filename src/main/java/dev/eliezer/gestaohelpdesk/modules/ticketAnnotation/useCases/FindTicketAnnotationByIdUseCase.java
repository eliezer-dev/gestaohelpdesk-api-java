package dev.eliezer.gestaohelpdesk.modules.ticketAnnotation.useCases;

import dev.eliezer.gestaohelpdesk.modules.ticketAnnotation.dtos.TicketAnnotationResponseDTO;
import dev.eliezer.gestaohelpdesk.modules.ticketAnnotation.mappers.TicketAnnotationMapper;
import dev.eliezer.gestaohelpdesk.modules.ticketAnnotation.repositories.TicketAnnotationRepository;
import dev.eliezer.gestaohelpdesk.service.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FindTicketAnnotationByIdUseCase {

    @Autowired
    private TicketAnnotationRepository ticketAnnotationRepository;

    @Autowired
    private TicketAnnotationMapper ticketAnnotationMapper;

    public TicketAnnotationResponseDTO execute(Long id) {
        var ticketAnnotation = ticketAnnotationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));

        return ticketAnnotationMapper.formatTicketAnnotationForTicketAnnotationResponseDTO(ticketAnnotation);
    }
}
