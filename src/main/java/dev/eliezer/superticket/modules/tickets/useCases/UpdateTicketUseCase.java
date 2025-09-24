package dev.eliezer.superticket.modules.tickets.useCases;

import dev.eliezer.superticket.dto.TicketRequestDTO;
import dev.eliezer.superticket.dto.TicketResponseDTO;
import dev.eliezer.superticket.modules.tickets.entities.Ticket;
import dev.eliezer.superticket.modules.tickets.repositories.TicketRepository;
import dev.eliezer.superticket.modules.tickets.services.validations.TicketValidator;
import dev.eliezer.superticket.service.exception.BusinessException;
import dev.eliezer.superticket.service.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static dev.eliezer.superticket.modules.tickets.mappers.TicketMapper.formatTicketRequestDTOForTicket;
import static dev.eliezer.superticket.modules.tickets.mappers.TicketMapper.formatTicketToTicketResponseDTO;

@Service
public class UpdateTicketUseCase {
    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TicketValidator ticketValidator;

    public TicketResponseDTO execute(Long id, TicketRequestDTO ticketRequestDTO) {
        Ticket ticketToChange =  ticketRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        ticketValidator.validate(ticketRequestDTO);
        Ticket ticket = formatTicketRequestDTOForTicket(ticketRequestDTO);
        ticketToChange.setClient(ticket.getClient());
        ticketToChange.setShortDescription(ticket.getShortDescription());
        ticketToChange.setDescription(ticket.getDescription());
        ticketToChange.setStatus(ticket.getStatus());
        ticketToChange.setUser(ticket.getUser());
        ticketToChange.setTypeOfService(ticket.getTypeOfService());
        ticketToChange.setScheduledDateTime(ticket.getScheduledDateTime());
        ticketToChange.setCategory(ticket.getCategory());
        ticketRepository.save(ticketToChange);
        ticketToChange = ticketRepository.findById(id).orElseThrow(() -> new BusinessException("Erro ao salvar o ticket."));
        var ticketResponse = formatTicketToTicketResponseDTO(ticketToChange);
        return ticketResponse;

    }



}
