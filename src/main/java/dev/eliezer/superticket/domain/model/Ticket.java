package dev.eliezer.superticket.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity(name = "tb_tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @NotBlank(message = "[shortDescription] is not provided.")
    private String shortDescription;
    @Column(nullable = false)
    @NotBlank(message = "[shortDescription] is not provided.")
    private String description;
    @ManyToOne(fetch = FetchType.EAGER)
    private Client client;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<User> user;

    @ManyToOne(fetch = FetchType.EAGER)
    private Status status;
/*    private LocalDate dateStart;
    private LocalDate dateEnd;*/



}
