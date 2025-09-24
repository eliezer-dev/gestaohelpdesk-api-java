package dev.eliezer.gestaohelpdesk.modules.ticketAnnotation.useCases;

import dev.eliezer.gestaohelpdesk.modules.ticketAnnotation.repositories.TicketAnnotationRepository;
import dev.eliezer.gestaohelpdesk.service.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteTicketAnnotationUseCase {


    @Autowired
    private TicketAnnotationRepository ticketAnnotationRepository;

    public void execute(Long id) {
        var ticketAnnotationToDelete = ticketAnnotationRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        ticketAnnotationRepository.delete(ticketAnnotationToDelete);
    }
}
