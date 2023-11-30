package dev.eliezer.superticket.domain.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity(name = "tb_tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String shortDescription;
    @Column(nullable = false)
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
