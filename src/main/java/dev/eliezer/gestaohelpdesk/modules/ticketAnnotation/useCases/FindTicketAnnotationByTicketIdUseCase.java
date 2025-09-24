package dev.eliezer.gestaohelpdesk.modules.ticketAnnotation.useCases;

import dev.eliezer.gestaohelpdesk.modules.ticketAnnotation.dtos.TicketAnnotationResponseDTO;
import dev.eliezer.gestaohelpdesk.modules.ticketAnnotation.mappers.TicketAnnotationMapper;
import dev.eliezer.gestaohelpdesk.modules.ticketAnnotation.repositories.TicketAnnotationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FindTicketAnnotationByTicketIdUseCase {

    @Autowired
    private TicketAnnotationRepository ticketAnnotationRepository;

    @Autowired
    private TicketAnnotationMapper ticketAnnotationMapper;

    public List<TicketAnnotationResponseDTO> execute(Long ticketId) {
        List<TicketAnnotationResponseDTO> response = new ArrayList<>();
        var ticketAnnotationList = ticketAnnotationRepository.findByTicketId(ticketId);
        ticketAnnotationList.forEach(ticketAnnotation -> {
            response.add(ticketAnnotationMapper.formatTicketAnnotationForTicketAnnotationResponseDTO(ticketAnnotation));
        });
        return response;
    }
}
