package dev.eliezer.superticket.domain.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity(name = "tb_tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    @Column(nullable = false)
    private String shortDescription;
//    @Column(nullable = false)
    private String description;
    @ManyToOne(fetch = FetchType.EAGER)
    private Client client;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<User> user;

    @ManyToOne(fetch = FetchType.EAGER)
    private Status status;
/*    private LocalDate dateStart;
    private LocalDate dateEnd;*/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

//    public LocalDate getDateStart() {
//        return dateStart;
//    }
//
//    public void setDateStart(LocalDate dateStart) {
//        this.dateStart = dateStart;
//    }
//
//    public LocalDate getDateEnd() {
//        return dateEnd;
//    }
//
//    public void setDateEnd(LocalDate dateEnd) {
//        this.dateEnd = dateEnd;
//    }
}
