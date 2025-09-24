package dev.eliezer.superticket.modules.ticketAnnotation.useCases;

import dev.eliezer.superticket.dto.TicketAnnotationResponseDTO;
import dev.eliezer.superticket.modules.ticketAnnotation.mappers.TicketAnnotationMapper;
import dev.eliezer.superticket.modules.ticketAnnotation.repositories.TicketAnnotationRepository;
import dev.eliezer.superticket.service.exception.NotFoundException;
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
