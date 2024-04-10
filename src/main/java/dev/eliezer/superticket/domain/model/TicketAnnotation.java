package dev.eliezer.superticket.domain.model;

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
    private LocalDateTime createAt;

    @UpdateTimestamp
    private LocalDateTime updateAt;
}
