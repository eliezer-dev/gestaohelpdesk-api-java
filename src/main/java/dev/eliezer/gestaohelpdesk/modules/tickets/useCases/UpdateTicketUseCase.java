package dev.eliezer.gestaohelpdesk.modules.tickets.useCases;

import dev.eliezer.gestaohelpdesk.modules.ai.dtos.SummarizeTicket;
import dev.eliezer.gestaohelpdesk.modules.ai.useCases.SummarizeTicketUseCase;
import dev.eliezer.gestaohelpdesk.modules.ticketAnnotation.entities.TicketAnnotation;
import dev.eliezer.gestaohelpdesk.modules.ticketAnnotation.repositories.TicketAnnotationRepository;
import dev.eliezer.gestaohelpdesk.modules.tickets.dtos.TicketRequestDTO;
import dev.eliezer.gestaohelpdesk.modules.tickets.dtos.TicketResponseDTO;
import dev.eliezer.gestaohelpdesk.modules.tickets.entities.Ticket;
import dev.eliezer.gestaohelpdesk.modules.tickets.repositories.TicketRepository;
import dev.eliezer.gestaohelpdesk.modules.tickets.services.validations.TicketValidator;
import dev.eliezer.gestaohelpdesk.service.exception.BusinessException;
import dev.eliezer.gestaohelpdesk.service.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import static dev.eliezer.gestaohelpdesk.modules.tickets.mappers.TicketMapper.formatTicketRequestDTOForTicket;
import static dev.eliezer.gestaohelpdesk.modules.tickets.mappers.TicketMapper.formatTicketToTicketResponseDTO;

@Service
public class UpdateTicketUseCase {
    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TicketAnnotationRepository ticketAnnotationRepository;

    @Autowired
    private TicketValidator ticketValidator;

    @Autowired
    private SummarizeTicketUseCase summarizeTicketUseCase;

    public TicketResponseDTO execute(Long id, TicketRequestDTO ticketRequestDTO) {
        SummarizeTicket summarizeTicket = new SummarizeTicket();

        Ticket ticketToChange =  ticketRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        ticketValidator.validate(ticketRequestDTO);
        Ticket ticket = formatTicketRequestDTOForTicket(ticketRequestDTO);
        ticketToChange.setClient(ticket.getClient());
        ticketToChange.setShortDescription(ticket.getShortDescription());
        ticketToChange.setDescription(ticket.getDescription());
        ticketToChange.setStatus(ticket.getStatus());
        ticketToChange.setUser(ticket.getUser());
        ticketToChange.setTypeOfService(ticket.getTypeOfService());
        ticketToChange.setScheduledDateTime(ticket.getScheduledDateTime());
        ticketToChange.setCategory(ticket.getCategory());

        if (ticketToChange.getStatus().getId() == 2) {
            summarizeTicket.setDescription(ticketToChange.getDescription());
            summarizeTicket.setShortDescription(ticketToChange.getShortDescription());

            summarizeTicket.setTicketAnnotations(ticketAnnotationRepository.findByTicketId(ticketToChange.getId()));

            String dados = "Descrição: " + summarizeTicket.getDescription() + "\n" + "Resumo: " + summarizeTicket.getShortDescription() + "\n";

            if (!summarizeTicket.getTicketAnnotations().isEmpty()) {
                dados += "Anotações: \n";

                for (TicketAnnotation ticketAnnotation : summarizeTicket.getTicketAnnotations()) {
                    dados += "Atendendente: " +  ticketAnnotation.getUser().getName();
                    dados += "Anotação: " + ticketAnnotation.getDescription() + "\n";
                }

            }

            String aiResponse =  summarizeTicketUseCase.execute("Você está recebendo os dados de um chamado" +
                    "helpdesk com o titulo, descriçã do chamado e as anotações feitas pelo atendente. Retorne um resumo " +
                    "do que aconteceu nesse chamado com a seguinte estrutura: \n" +
                    "Resumo do Problema, Solução feita pelo atendente e Foi resolvido?" + "\n" +
                    dados);
            System.out.println(aiResponse);
            ticketToChange.setAiSummarizeTicket(aiResponse);

        }


        ticketRepository.save(ticketToChange);
        ticketToChange = ticketRepository.findById(id).orElseThrow(() -> new BusinessException("Erro ao salvar o ticket."));





        var ticketResponse = formatTicketToTicketResponseDTO(ticketToChange);
        return ticketResponse;

    }



}
