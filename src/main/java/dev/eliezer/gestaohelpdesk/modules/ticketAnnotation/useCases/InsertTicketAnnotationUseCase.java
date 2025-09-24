package dev.eliezer.gestaohelpdesk.modules.ticketAnnotation.useCases;

import dev.eliezer.gestaohelpdesk.dto.TicketAnnotationRequestDTO;
import dev.eliezer.gestaohelpdesk.dto.TicketAnnotationResponseDTO;
import dev.eliezer.gestaohelpdesk.modules.ticketAnnotation.mappers.TicketAnnotationMapper;
import dev.eliezer.gestaohelpdesk.modules.ticketAnnotation.repositories.TicketAnnotationRepository;
import dev.eliezer.gestaohelpdesk.modules.ticketAnnotation.services.validations.TicketAnnotationValidator;
import dev.eliezer.gestaohelpdesk.service.exception.BusinessException;
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
