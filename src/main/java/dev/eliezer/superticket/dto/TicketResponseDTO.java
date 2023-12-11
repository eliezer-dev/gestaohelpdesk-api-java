package dev.eliezer.superticket.dto;

import dev.eliezer.superticket.domain.model.Client;
import dev.eliezer.superticket.domain.model.Status;
import dev.eliezer.superticket.domain.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketResponseDTO {
    private Long id;
    private String shortDescription;
    private String description;
    private Client client;
    private List<UserForTicketResponseDTO> users;
    private Status status;
}
