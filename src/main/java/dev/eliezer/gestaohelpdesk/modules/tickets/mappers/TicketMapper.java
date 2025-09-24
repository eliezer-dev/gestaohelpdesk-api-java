package dev.eliezer.gestaohelpdesk.modules.tickets.mappers;

import dev.eliezer.gestaohelpdesk.modules.category.entities.Category;
import dev.eliezer.gestaohelpdesk.modules.client.entities.Client;
import dev.eliezer.gestaohelpdesk.modules.status.entities.Status;
import dev.eliezer.gestaohelpdesk.dto.ClientForTicketResponseDTO;
import dev.eliezer.gestaohelpdesk.dto.TicketRequestDTO;
import dev.eliezer.gestaohelpdesk.dto.TicketResponseDTO;
import dev.eliezer.gestaohelpdesk.dto.UserForTicketResponseDTO;
import dev.eliezer.gestaohelpdesk.modules.tickets.entities.Ticket;
import dev.eliezer.gestaohelpdesk.modules.user.entities.User;
import lombok.Data;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class TicketMapper {

    public static TicketResponseDTO formatTicketToTicketResponseDTO(Ticket ticket){
        UserForTicketResponseDTO userResponse = new UserForTicketResponseDTO();
        if (ticket.getUser().size() > 0) {
            userResponse = UserForTicketResponseDTO.builder()
                    .id(ticket.getUser().get(0).getId())
                    .name(ticket.getUser().get(0).getName())
                    .build();
        }


        ClientForTicketResponseDTO clientForTicketResponseDTO = ClientForTicketResponseDTO.builder()
                .id(ticket.getClient().getId())
                .razaoSocialName(ticket.getClient().getRazaoSocialName())
                .businessName(ticket.getClient().getBusinessName())
                .cpfCnpj(ticket.getClient().getCpfCnpj())
                .cep(ticket.getClient().getCep())
                .address(ticket.getClient().getAddress())
                .addressNumber(ticket.getClient().getAddressNumber())
                .neighborhood(ticket.getClient().getNeighborhood())
                .city(ticket.getClient().getCity())
                .state(ticket.getClient().getState())
                .email(ticket.getClient().getEmail())
                .slaDefault(ticket.getClient().getSlaDefault())
                .slaUrgency(ticket.getClient().getSlaUrgency())
                .build();

        Duration slaInHours = null;
        LocalDateTime slaDateTimeEnd = null;
        if (ticket.getCategory().getPriority() != null) {
            slaInHours = Duration.ofHours(ticket.getCategory().getPriority() == 0 ? ticket.getClient().getSlaDefault() :
                    ticket.getClient().getSlaUrgency());
            slaDateTimeEnd = ticket.getCreateAt().plus(slaInHours);
        }

        var ticketsReponse = TicketResponseDTO.builder()
                .id(ticket.getId())
                .description(ticket.getDescription())
                .shortDescription(ticket.getShortDescription())
                .client(clientForTicketResponseDTO)
                .status(ticket.getStatus())
                .user(userResponse)
                .typeOfService(ticket.getTypeOfService())
                .category(ticket.getCategory())
                .scheduledDateTime(ticket.getScheduledDateTime())
                .createAt(ticket.getCreateAt())
                .updateAt(!(ticket.getUpdateAt() == null) ? ticket.getUpdateAt() : ticket.getCreateAt())
                .slaDateTimeEnd(slaDateTimeEnd)
                .build();
        return ticketsReponse;
    }


    public static Ticket formatTicketRequestDTOForTicket(TicketRequestDTO ticketRequestDTO){
        List<User> users = new ArrayList<>();
        ticketRequestDTO.getUsers().forEach(userForTicketRequestDTO -> {
            User user = new User();
            user.setId(userForTicketRequestDTO.getId());
            users.add(user);
        });
        Client client = new Client();
        client.setId(ticketRequestDTO.getClient().getId());

        Status status = new Status();
        status.setId(ticketRequestDTO.getStatus().getId());

        Category category = new Category();
        category.setId(ticketRequestDTO.getCategory().getId());

        Ticket ticket = Ticket.builder()
                .id(ticketRequestDTO.getId())
                .shortDescription(ticketRequestDTO.getShortDescription())
                .description(ticketRequestDTO.getDescription())
                .user(users)
                .client(client)
                .status(status)
                .typeOfService(ticketRequestDTO.getTypeOfService())
//                .scheduledDateTime(LocalDateTime
//                        .parse(ticketRequestDTO.getScheduledDateTime(),
//                                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .scheduledDateTime(ticketRequestDTO.getScheduledDateTime())
                .category(category)
                .build();
        return ticket;
    }

}
