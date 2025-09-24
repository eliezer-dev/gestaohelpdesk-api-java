package dev.eliezer.gestaohelpdesk.modules.tickets.services.validations;

import dev.eliezer.gestaohelpdesk.modules.client.repositories.ClientRepository;
import dev.eliezer.gestaohelpdesk.modules.status.repositories.StatusRepository;
import dev.eliezer.gestaohelpdesk.dto.TicketRequestDTO;
import dev.eliezer.gestaohelpdesk.modules.tickets.repositories.TicketRepository;
import dev.eliezer.gestaohelpdesk.modules.user.repositories.UserRepository;
import dev.eliezer.gestaohelpdesk.service.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketValidator {
    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StatusRepository statusRepository;

    public void validate(TicketRequestDTO ticketRequestDTO){

        if (ticketRequestDTO.getClient() == null)
            throw new BusinessException("Client is not provided");
        if (ticketRequestDTO.getStatus() == null)
            throw new BusinessException("Status is not provided");

        //checkIfClientExists
        if (ticketRequestDTO.getClient().getId() == null)
            throw new BusinessException("Client is not provided");
        if (!clientRepository.existsById(ticketRequestDTO.getClient().getId()))
            throw new BusinessException("Client with id " + ticketRequestDTO.getClient().getId() + " does not exist.");

        if (ticketRequestDTO.getUsers() != null) {
            ticketRequestDTO.getUsers().forEach(user ->{
                if (user.getId() == null) {
                    throw new BusinessException("User id is invalid");
                }

                if (!userRepository.existsById(user.getId())) {
                    throw new BusinessException("User with id " + user.getId() + " does not exist.\n" +
                            "The operation was aborted.");
                }

            });
        }


        //checkIfStatusExists
        if (ticketRequestDTO.getStatus().getId() == null)
            throw new BusinessException("Status is not provided");

        if (!statusRepository.existsById(ticketRequestDTO.getStatus().getId()))
            throw new BusinessException("Status with id " + ticketRequestDTO.getStatus().getId() + " is not exists.");

        if (ticketRequestDTO.getTypeOfService() == null)
            throw new BusinessException("Type Of Service is not provided");

        if (ticketRequestDTO.getCategory() == null)
            throw new BusinessException("Category is not provided");
    }
}
