package dev.eliezer.superticket.service.impl;

import dev.eliezer.superticket.domain.model.Ticket;
import dev.eliezer.superticket.domain.model.User;
import dev.eliezer.superticket.domain.repository.ClientRepository;
import dev.eliezer.superticket.domain.repository.StatusRepository;
import dev.eliezer.superticket.domain.repository.TicketRepository;
import dev.eliezer.superticket.domain.repository.UserRepository;
import dev.eliezer.superticket.service.TicketService;
import dev.eliezer.superticket.service.exception.BusinessException;
import dev.eliezer.superticket.service.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StatusRepository statusRepository;

    @Override
    public Iterable<Ticket> findAll() {
        return ticketRepository.findAll();
    }

    @Override
    public Ticket findById(Long id) {
        return ticketRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    @Override
    public Ticket insert(Ticket ticket) {
        ticketValidator(ticket);
        return ticketRepository.save(ticket);
    }

    @Override
    public Ticket update(Long id, Ticket ticket) {
        Ticket ticketToChange =  ticketRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        ticketValidator(ticket);
        ticketToChange.setClient(ticket.getClient());
        ticketToChange.setShortDescription(ticket.getShortDescription());
        ticketToChange.setDescription(ticket.getDescription());;
        return ticketRepository.save(ticketToChange);
    }

    @Override
    public void delete(Long id) {
        Ticket ticketToDelete = ticketRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        ticketRepository.delete(ticketToDelete);
    }

    private void ticketValidator(Ticket ticket){

        if (ticket.getUser() == null)
            throw new BusinessException("User is not provided");
        if (ticket.getClient() == null)
            throw new BusinessException("Client is not provided");
        if (ticket.getStatus() == null)
            throw new BusinessException("Status is not provided");

        //checkIfClientExists
        if (ticket.getClient().getId() == null)
            throw new BusinessException("Client is not provided");
        if (!clientRepository.existsById(ticket.getClient().getId()))
            throw new BusinessException("Client with id " + ticket.getClient().getId() + " does not exist.");

        //checkifUserExists
        ticket.getUser().forEach(user ->{
            if (user.getId() == null)
                throw new BusinessException("User is not provided");
            if (!userRepository.existsById(user.getId()))
                throw new BusinessException("User with id " + user.getId() + " does not exist.\n" +
                        "The operation was aborted.");
        });

        //checkIfStatusExists
        if (ticket.getStatus().getId() == null)
            throw new BusinessException("Status is not provided");

        if (!statusRepository.existsById(ticket.getStatus().getId()))
            throw new BusinessException("Status is not exists.");

    }

}
