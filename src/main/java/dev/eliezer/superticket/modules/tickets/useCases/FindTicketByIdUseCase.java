package dev.eliezer.superticket.modules.tickets.useCases;

import dev.eliezer.superticket.dto.TicketResponseDTO;
import dev.eliezer.superticket.modules.tickets.repositories.TicketRepository;
import dev.eliezer.superticket.service.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static dev.eliezer.superticket.modules.tickets.mappers.TicketMapper.formatTicketToTicketResponseDTO;

@Service
public class FindTicketByIdUseCase {

    @Autowired
    private TicketRepository ticketRepository;

    public TicketResponseDTO execute(Long id) {
        var ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));

        var ticketResponseDTO = formatTicketToTicketResponseDTO(ticket);

        return ticketResponseDTO;
    }
}
