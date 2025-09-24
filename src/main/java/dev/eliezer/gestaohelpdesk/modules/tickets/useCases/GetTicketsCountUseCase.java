package dev.eliezer.gestaohelpdesk.modules.tickets.useCases;

import dev.eliezer.gestaohelpdesk.dto.TicketCountResponseDTO;
import dev.eliezer.gestaohelpdesk.modules.tickets.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetTicketsCountUseCase {

    @Autowired
    private TicketRepository ticketRepository;

    public TicketCountResponseDTO execute(Long userId) {
        Long allTicketsCount = (long) ticketRepository.findAllByStatusTypeNotClosed().size();
        Long ticketsAssignedUserCount = (long) ticketRepository.findByUserIdAndNotStatusTypeClosed(userId).size();
        Long ticketsAssignedOtherUsersCount = (long) ticketRepository.findByNotUserIdAndNotStatusTypeClosed(userId).size();
        Long ticketsNotAssignedCount = (long) ticketRepository.findByTicketsWithoutUserAndNotStatusTypeClosed().size();
        Long completedTicketsCount = (long) ticketRepository.findAllCompletedTickets().size();
        Long closedTicketsCount = (long) ticketRepository.findAllClosedTickets().size();
        TicketCountResponseDTO ticketCountResponseDTO = TicketCountResponseDTO.builder()
                .allTicketsCount(allTicketsCount)
                .ticketsAssignedUserCount(ticketsAssignedUserCount)
                .ticketsAssignedOtherUsersCount(ticketsAssignedOtherUsersCount)
                .ticketsNotAssignedCount(ticketsNotAssignedCount)
                .completedTicketsCount(completedTicketsCount)
                .closedTicketsCount(closedTicketsCount)
                .build();
        return ticketCountResponseDTO;
    }
}
