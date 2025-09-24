package dev.eliezer.superticket.modules.ticketAnnotation.services.validations;

import dev.eliezer.superticket.dto.TicketAnnotationRequestDTO;
import dev.eliezer.superticket.modules.tickets.repositories.TicketRepository;
import dev.eliezer.superticket.modules.user.repositories.UserRepository;
import dev.eliezer.superticket.service.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketAnnotationValidator {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private UserRepository userRepository;

    public void validade(TicketAnnotationRequestDTO request) {

        if (request.getDescription() == null) {
            throw new BusinessException("Description is not provided.");
        }

        if (request.getTicketId() == null) {
            throw new BusinessException("Ticket id is not provided.");
        }

        if (request.getUserId() == null) {
            throw new BusinessException("User id is not provided.");
        }

        if (!ticketRepository.existsById(request.getTicketId())) {
            throw new BusinessException("Ticket with id " + request.getTicketId() + " does not exists.");
        }
        if (!userRepository.existsById(request.getUserId())) {
            throw new BusinessException("User with id " + request.getUserId() + " does not exists.");
        }

    }

}
