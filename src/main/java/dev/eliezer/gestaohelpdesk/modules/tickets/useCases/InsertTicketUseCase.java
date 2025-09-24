package dev.eliezer.gestaohelpdesk.modules.tickets.useCases;

import dev.eliezer.gestaohelpdesk.modules.client.repositories.ClientRepository;
import dev.eliezer.gestaohelpdesk.modules.status.repositories.StatusRepository;
import dev.eliezer.gestaohelpdesk.dto.TicketRequestDTO;
import dev.eliezer.gestaohelpdesk.dto.TicketResponseDTO;
import dev.eliezer.gestaohelpdesk.modules.tickets.entities.Ticket;
import dev.eliezer.gestaohelpdesk.modules.tickets.repositories.TicketRepository;
import dev.eliezer.gestaohelpdesk.modules.tickets.services.validations.TicketValidator;
import dev.eliezer.gestaohelpdesk.modules.user.repositories.UserRepository;
import dev.eliezer.gestaohelpdesk.service.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static dev.eliezer.gestaohelpdesk.modules.tickets.mappers.TicketMapper.formatTicketRequestDTOForTicket;
import static dev.eliezer.gestaohelpdesk.modules.tickets.mappers.TicketMapper.formatTicketToTicketResponseDTO;

@Service
public class InsertTicketUseCase {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private TicketValidator ticketValidator;

    public TicketResponseDTO execute(TicketRequestDTO ticketRequestDTO) {

        ticketValidator.validate(ticketRequestDTO);
        Ticket newTicket = formatTicketRequestDTOForTicket(ticketRequestDTO);

        Ticket response = ticketRepository.save(newTicket);
        Ticket ticketSaved = ticketRepository.findById(response.getId())
                .orElseThrow(() -> new BusinessException("Erro ao salvar Ticket"));
        var ticketResponse = formatTicketToTicketResponseDTO(ticketSaved);
        return ticketResponse;
    }




}
