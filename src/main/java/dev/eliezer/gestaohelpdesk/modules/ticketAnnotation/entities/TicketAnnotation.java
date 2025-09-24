package dev.eliezer.gestaohelpdesk.modules.ticketAnnotation.entities;

import dev.eliezer.gestaohelpdesk.modules.tickets.entities.Ticket;
import dev.eliezer.gestaohelpdesk.modules.user.entities.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Entity(name = "tb_tickets_annotations")
public class TicketAnnotation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(example = "9",
            requiredMode = Schema.RequiredMode.REQUIRED, description = "id of annotation")
    private Long id;

    @Column(nullable = false, length = 1000)
    @NotBlank(message = "[description] is not provided.")
    @Schema(example = "Fiz diversos testes relacionados ao problema descrito e não apresentou erro.", requiredMode = Schema.RequiredMode.REQUIRED, description = "description of annotation")
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    private Ticket ticket;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @CreationTimestamp
    @Schema(example = "2024-03-25T10:30:00", description ="date and time the ticket annotation was created")
    private LocalDateTime createAt;

    @UpdateTimestamp
    @Schema(example = "2024-03-25T10:30:00", description ="date and time the ticket annotation was updated")
    private LocalDateTime updateAt;
}
