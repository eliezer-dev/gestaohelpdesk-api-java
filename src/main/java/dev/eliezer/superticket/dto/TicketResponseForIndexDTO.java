package dev.eliezer.superticket.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketResponseForIndexDTO {
    private List<TicketResponseDTO> allTickets;

    private List<TicketResponseDTO> ticketsAssignedUser;

    private List<TicketResponseDTO> ticketsNotAssigned;

    private List<TicketResponseDTO> ticketsAssignedOtherUsers;
}
