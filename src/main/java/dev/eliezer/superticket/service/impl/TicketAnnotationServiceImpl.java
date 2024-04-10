package dev.eliezer.superticket.service.impl;

import dev.eliezer.superticket.domain.model.Ticket;
import dev.eliezer.superticket.domain.model.TicketAnnotation;
import dev.eliezer.superticket.domain.model.User;
import dev.eliezer.superticket.domain.repository.TicketAnnotationRepository;
import dev.eliezer.superticket.domain.repository.TicketRepository;
import dev.eliezer.superticket.domain.repository.UserRepository;
import dev.eliezer.superticket.dto.TicketAnnotationRequestDTO;
import dev.eliezer.superticket.dto.TicketAnnotationResponseDTO;
import dev.eliezer.superticket.dto.TicketRequestDTO;
import dev.eliezer.superticket.service.TicketAnnotationService;
import dev.eliezer.superticket.service.exception.BusinessException;
import dev.eliezer.superticket.service.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TicketAnnotationServiceImpl implements TicketAnnotationService {
    @Autowired
    private TicketAnnotationRepository ticketAnnotationRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<TicketAnnotationResponseDTO> findByTicketId(Long ticketId) {
        List<TicketAnnotationResponseDTO> response = new ArrayList<>();
        var ticketAnnotationList = ticketAnnotationRepository.findByTicketId(ticketId);
        ticketAnnotationList.forEach(ticketAnnotation -> {
            response.add(formatTicketAnnotationForTicketAnnotationResponseDTO(ticketAnnotation));
        });
        return response;
    }

    @Override
    public TicketAnnotationResponseDTO findById(Long id) {
        var ticketAnnotation = ticketAnnotationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));

        return formatTicketAnnotationForTicketAnnotationResponseDTO(ticketAnnotation);
    }

    @Override
    public TicketAnnotationResponseDTO insert(TicketAnnotationRequestDTO request) {
        ticketAnnotationValidator(request);
        var ticketAnnotation = formatTicketAnnotationRequestDTOForTicketAnnotation(request);
        var ticketAnnotationInserted = ticketAnnotationRepository.save(ticketAnnotation);
        if (ticketAnnotationInserted.getId() == null) {
            throw new BusinessException("Erro ao salvar o ticket.");
        }
        return formatTicketAnnotationForTicketAnnotationResponseDTO(ticketAnnotationInserted);
    }

    @Override
    public void delete(Long id) {
        var ticketAnnotationToDelete = ticketAnnotationRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        ticketAnnotationRepository.delete(ticketAnnotationToDelete);
    }

    private TicketAnnotation formatTicketAnnotationRequestDTOForTicketAnnotation (TicketAnnotationRequestDTO ticketAnnotationRequestDTO) {

        TicketAnnotation ticketAnnotation = new TicketAnnotation();

        Ticket ticket = new Ticket();
        ticket.setId(ticketAnnotationRequestDTO.getTicketId());

        User user = new User();
        user.setId(ticketAnnotationRequestDTO.getUserId());

        ticketAnnotation.setTicket(ticket);
        ticketAnnotation.setUser(user);
        ticketAnnotation.setDescription(ticketAnnotationRequestDTO.getDescription());

        return ticketAnnotation;
    }

    private void ticketAnnotationValidator(TicketAnnotationRequestDTO request) {

        if (request.getDescription() == null) {
            throw new BusinessException("Description is not provided.");
        }

        if (request.getTicketId() == null) {
            throw new BusinessException("Ticket id is not provided.");
        }

        if (request.getUserId() == null) {
            throw new BusinessException("User id is not provided.");
        }

        if (!ticketRepository.existsById(request.getTicketId())) {
            throw new BusinessException("Ticket with id " + request.getTicketId() + " does not exists.");
        }
        if (!userRepository.existsById(request.getUserId())) {
            throw new BusinessException("User with id " + request.getUserId() + " does not exists.");
        }

    }

    private TicketAnnotationResponseDTO formatTicketAnnotationForTicketAnnotationResponseDTO(TicketAnnotation ticketAnnotation) {
        TicketAnnotationResponseDTO ticketAnnotationResponseDTO = TicketAnnotationResponseDTO.builder()
                .id(ticketAnnotation.getId())
                .ticketId(ticketAnnotation.getTicket().getId())
                .userId(ticketAnnotation.getUser().getId())
                .description(ticketAnnotation.getDescription())
                .build();
        return ticketAnnotationResponseDTO;
    }
}


