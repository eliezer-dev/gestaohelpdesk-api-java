package dev.eliezer.superticket.service.impl;

import dev.eliezer.superticket.domain.model.*;
import dev.eliezer.superticket.domain.repository.ClientRepository;
import dev.eliezer.superticket.domain.repository.StatusRepository;
import dev.eliezer.superticket.domain.repository.TicketRepository;
import dev.eliezer.superticket.domain.repository.UserRepository;
import dev.eliezer.superticket.dto.*;
import dev.eliezer.superticket.service.TicketService;
import dev.eliezer.superticket.service.exception.BusinessException;
import dev.eliezer.superticket.service.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


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

    @Autowired
    private UserServiceImpl userService;

    @Override
    public TicketResponseForIndexDTO index(Long userId) {
        List<TicketResponseDTO> allTickets = new ArrayList<>();
        final List<TicketResponseDTO> ticketsAssignedUser = new ArrayList<>();
        final List<TicketResponseDTO> ticketsNotAssignedUser = new ArrayList<>();
        final List<TicketResponseDTO> ticketsAssignedOtherUsers = new ArrayList<>();
        TicketResponseForIndexDTO ticketResponseForIndexDTO;
        ticketRepository.findAll().forEach(ticket -> {
            var ticketDTO = formatTicketToTicketResponseDTO(ticket);
            allTickets.add(ticketDTO);
        });

        allTickets.stream().forEach((ticket) -> {
            ticket.getUsers().stream().forEach((user) -> {
                if (user.getId().equals(userId)) {
                    ticketsAssignedUser.add(ticket);
                }
            });
        });


        allTickets.stream().forEach((ticket) -> {
            if(ticket.getUsers().isEmpty()) {
                ticketsNotAssignedUser.add(ticket);
            }

        });

        //garante que em ticketsAssignedOtherUsers só tenha os tickets que estão atribuidos a outros usuarios
        List<TicketResponseDTO> compareList = new ArrayList<>();
        compareList.addAll(ticketsAssignedUser);
        compareList.addAll(ticketsNotAssignedUser);
        ticketsAssignedOtherUsers.addAll(allTickets);
        ticketsAssignedOtherUsers.removeAll(compareList);

        ticketResponseForIndexDTO = TicketResponseForIndexDTO.builder()
                .allTickets(allTickets)
                .ticketsNotAssigned(ticketsNotAssignedUser)
                .ticketsAssignedUser(ticketsAssignedUser)
                .ticketsAssignedOtherUsers(ticketsAssignedOtherUsers)
                .build();

        return ticketResponseForIndexDTO;
    }

    @Override
    public TicketResponseDTO findById(Long id) {
        var ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
        var ticketResponseDTO = formatTicketToTicketResponseDTO(ticket);

        return ticketResponseDTO;
    }


    @Override
    public TicketResponseDTO insert(TicketRequestDTO ticketRequestDTO) {
        ticketValidator(ticketRequestDTO);
        Ticket ticketInserted = formatTicketRequestDTOForTicket(ticketRequestDTO);

        ticketRepository.save(ticketInserted);
        ticketInserted = ticketRepository.findById(ticketInserted.getId())
                .orElseThrow(() -> new BusinessException("Erro ao salvar Ticket"));

        var ticketResponse = formatTicketToTicketResponseDTO(ticketInserted);
        return ticketResponse;
        }


    @Override
    public TicketResponseDTO update(Long id, TicketRequestDTO ticketRequestDTO) {
        Ticket ticketToChange =  ticketRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        ticketValidator(ticketRequestDTO);
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

    @Override
    public void delete(Long id) {
        Ticket ticketToDelete = ticketRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        ticketRepository.delete(ticketToDelete);
    }

    private void ticketValidator(TicketRequestDTO ticketRequestDTO){

        if (ticketRequestDTO.getUsers() == null)
            throw new BusinessException("User is not provided");
        if (ticketRequestDTO.getClient() == null)
            throw new BusinessException("Client is not provided");
        if (ticketRequestDTO.getStatus() == null)
            throw new BusinessException("Status is not provided");

        //checkIfClientExists
        if (ticketRequestDTO.getClient().getId() == null)
            throw new BusinessException("Client is not provided");
        if (!clientRepository.existsById(ticketRequestDTO.getClient().getId()))
            throw new BusinessException("Client with id " + ticketRequestDTO.getClient().getId() + " does not exist.");

        //checkifUserExists
        ticketRequestDTO.getUsers().forEach(user ->{
            if (user.getId() == null)
                throw new BusinessException("User is not provided");
            if (!userRepository.existsById(user.getId()))
                throw new BusinessException("User with id " + user.getId() + " does not exist.\n" +
                        "The operation was aborted.");
        });

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

    private TicketResponseDTO formatTicketToTicketResponseDTO(Ticket ticket){

        List<UserForTicketResponseDTO> usersResponse = new ArrayList<>();
        ticket.getUser().forEach(user -> {
            var userResponse = UserForTicketResponseDTO.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .build();
             usersResponse.add(userResponse);
        });
        ClientForTicketResponseDTO clientForTicketResponseDTO = ClientForTicketResponseDTO.builder()
                .id(ticket.getClient().getId())
                .razaoSocialName(ticket.getClient().getRazaoSocialName())
                .businessName(ticket.getClient().getBusinessName())
                .cpfCnpj(ticket.getClient().getCpfCnpj())
                .address(ticket.getClient().getAddress())
                .addressNumber(ticket.getClient().getAddressNumber())
                .state(ticket.getClient().getState())
                .city(ticket.getClient().getCity())
                .cep(ticket.getClient().getCep())
                .email(ticket.getClient().getEmail())
                .build();

        var ticketsReponse = TicketResponseDTO.builder()
                .id(ticket.getId())
                .description(ticket.getDescription())
                .shortDescription(ticket.getShortDescription())
                .client(clientForTicketResponseDTO)
                .status(ticket.getStatus())
                .users(usersResponse)
                .typeOfService(ticket.getTypeOfService())
                .category(ticket.getCategory())
                .scheduledDateTime(ticket.getScheduledDateTime())
                .createAt(ticket.getCreateAt())
                .build();
        return ticketsReponse;
    }

    private Ticket formatTicketRequestDTOForTicket(TicketRequestDTO ticketRequestDTO){
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
