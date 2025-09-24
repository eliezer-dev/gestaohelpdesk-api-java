package dev.eliezer.gestaohelpdesk.modules.tickets.entities;

import dev.eliezer.gestaohelpdesk.modules.category.entities.Category;
import dev.eliezer.gestaohelpdesk.modules.client.entities.Client;
import dev.eliezer.gestaohelpdesk.modules.status.entities.Status;
import dev.eliezer.gestaohelpdesk.modules.user.entities.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity(name = "tb_tickets")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "[shortDescription] is not provided.")
    @Schema(example = "Erro no fechamento do caixa", requiredMode = Schema.RequiredMode.REQUIRED, description = "shortdescription of ticket")
    private String shortDescription;

    @Column(nullable = false, length = 1000)
    @NotBlank(message = "[shortDescription] is not provided.")
    @Schema(example = "Relatório de fechamento do caixa apresenta valores divergentes", requiredMode = Schema.RequiredMode.REQUIRED, description = "description of ticket")
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @Schema(example = "{\n"+"\"id:\"1}", requiredMode = Schema.RequiredMode.REQUIRED, description = "client of ticket")
    private Client client;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<User> user;

    @ManyToOne(fetch = FetchType.EAGER)
    private Status status;

    @Column(nullable = false)
    @Schema(example = "1", description =
            """
            1 - internal.
            2 - external.
            """)
    Long typeOfService;

    @Schema(example = "2024-03-25T10:30:00", description ="sheduled date and time of ticket")
    private LocalDateTime scheduledDateTime;

    @ManyToOne(fetch = FetchType.EAGER)
    private Category category;

    @CreationTimestamp
    @Schema(example = "2024-03-25T10:30:00", description ="date and time the ticket was created")
    private LocalDateTime createAt;

    @UpdateTimestamp
    @Schema(example = "2024-03-25T10:30:00", description ="date and time the ticket was updated")
    private LocalDateTime updateAt;




}
