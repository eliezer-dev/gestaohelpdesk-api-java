package dev.eliezer.superticket.service.impl;

import dev.eliezer.superticket.domain.model.Ticket;
import dev.eliezer.superticket.domain.repository.ClientRepository;
import dev.eliezer.superticket.domain.repository.StatusRepository;
import dev.eliezer.superticket.domain.repository.TicketRepository;
import dev.eliezer.superticket.domain.repository.UserRepository;
import dev.eliezer.superticket.service.TicketService;
import dev.eliezer.superticket.service.exception.BusinessException;
import org.springframework.stereotype.Service;


@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final ClientRepository clientRepository;
    private final UserRepository userRepository;
    private final StatusRepository statusRepository;

    private TicketServiceImpl(TicketRepository ticketRepository, ClientRepository clientRepository, UserRepository userRepository, StatusRepository statusRepository){
        this.ticketRepository = ticketRepository;
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
        this.statusRepository = statusRepository;
    }
    @Override
    public Iterable<Ticket> findAll() {
        return ticketRepository.findAll();
    }

    @Override
    public Ticket findById(Long id) {
        return ticketRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public Ticket insert(Ticket ticket) {
        ticketValidator(ticket);
        return ticketRepository.save(ticket);
    }

    @Override
    public Ticket update(Long id, Ticket ticket) {
        Ticket ticketToChange =  ticketRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        ticketToChange.setClient(ticket.getClient());
        ticketToChange.setShortDescription(ticket.getShortDescription());
        ticketToChange.setDescription(ticket.getDescription());;
        return ticketRepository.save(ticketToChange);
    }

    @Override
    public void delete(Long id) {
        ticketRepository.deleteById(id);
    }

    private void ticketValidator(Ticket ticket){
        //checkIfPropertiesIsNull
        if (ticket.getShortDescription() == null){
            throw new BusinessException("short description is not provided");
        }
        if (ticket.getDescription() == null){
            throw new BusinessException("description is not provided");
        }
        if (ticket.getUser() == null){
            throw new BusinessException("User is not provided");
        }
        if (ticket.getClient() == null){
            throw new BusinessException("Client is not provided");
        }
        if (ticket.getStatus() == null){
            throw new BusinessException("Status is not provided");
        }

        //checkIfClientExists
        if (ticket.getClient().getId() == null) {
            throw new BusinessException("Client is not provided");
        }
        if (!clientRepository.existsById(ticket.getClient().getId())) {
            throw new BusinessException("Client is not exists.");
        }

        //checkifUserExists
        ticket.getUser().forEach(user ->{
            if (user.getId() == null){
                throw new BusinessException("User is not provided");
            }
            if (!userRepository.existsById(user.getId())){
                throw new BusinessException("User with id " + user.getId() + " does not exist.\n" +
                        "The operation was aborted.");
            }
        });


        //checkIfStatusExists
        if (ticket.getStatus().getId() == null) {
            throw new BusinessException("Status is not provided");
        }
        if (!statusRepository.existsById(ticket.getStatus().getId())) {
            throw new BusinessException("Status is not exists.");
        }



    }


}
