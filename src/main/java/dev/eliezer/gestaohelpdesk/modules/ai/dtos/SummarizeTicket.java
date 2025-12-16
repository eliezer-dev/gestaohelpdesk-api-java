package dev.eliezer.gestaohelpdesk.modules.ai.dtos;

import dev.eliezer.gestaohelpdesk.modules.ticketAnnotation.entities.TicketAnnotation;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SummarizeTicket {

    private String description;

    private String shortDescription;

    private List<TicketAnnotation> ticketAnnotations;
}
