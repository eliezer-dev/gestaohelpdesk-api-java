package dev.eliezer.superticket.domain.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
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
    @Column(nullable = false)
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

    @CreationTimestamp
    private LocalDateTime createAt;




}
