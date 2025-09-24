package dev.eliezer.superticket.modules.tickets.useCases;

import dev.eliezer.superticket.modules.tickets.entities.Ticket;
import dev.eliezer.superticket.modules.tickets.repositories.TicketRepository;
import dev.eliezer.superticket.service.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteTicketUseCase {

    @Autowired
    private TicketRepository ticketRepository;

    public void execute(Long id) {
        Ticket ticketToDelete = ticketRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        ticketRepository.delete(ticketToDelete);
    }
}
