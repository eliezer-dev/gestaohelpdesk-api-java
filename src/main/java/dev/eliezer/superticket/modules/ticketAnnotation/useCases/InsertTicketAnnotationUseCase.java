package dev.eliezer.superticket.modules.ticketAnnotation.useCases;

import dev.eliezer.superticket.dto.TicketAnnotationRequestDTO;
import dev.eliezer.superticket.dto.TicketAnnotationResponseDTO;
import dev.eliezer.superticket.modules.ticketAnnotation.mappers.TicketAnnotationMapper;
import dev.eliezer.superticket.modules.ticketAnnotation.repositories.TicketAnnotationRepository;
import dev.eliezer.superticket.modules.ticketAnnotation.services.validations.TicketAnnotationValidator;
import dev.eliezer.superticket.service.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InsertTicketAnnotationUseCase {

    @Autowired
    private TicketAnnotationValidator ticketAnnotationValidator;

    @Autowired
    private TicketAnnotationMapper ticketAnnotationMapper;

    @Autowired
    private TicketAnnotationRepository ticketAnnotationRepository;

    public TicketAnnotationResponseDTO execute(TicketAnnotationRequestDTO request) {
        ticketAnnotationValidator.validade(request);
        var ticketAnnotation = ticketAnnotationMapper.formatTicketAnnotationRequestDTOForTicketAnnotation(request);
        var ticketAnnotationInserted = ticketAnnotationRepository.save(ticketAnnotation);
        if (ticketAnnotationInserted.getId() == null) {
            throw new BusinessException("Erro ao salvar o ticket.");
        }
        return ticketAnnotationMapper.formatTicketAnnotationForTicketAnnotationResponseDTO(ticketAnnotationInserted);
    }
}
