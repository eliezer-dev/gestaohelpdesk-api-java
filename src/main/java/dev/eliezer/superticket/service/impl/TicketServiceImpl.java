package dev.eliezer.superticket.service.impl;

import dev.eliezer.superticket.domain.model.Ticket;
import dev.eliezer.superticket.domain.repository.TicketRepository;
import dev.eliezer.superticket.service.TicketService;
import org.springframework.stereotype.Service;

@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;

    private TicketServiceImpl(TicketRepository ticketRepository){
        this.ticketRepository = ticketRepository;
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
}
