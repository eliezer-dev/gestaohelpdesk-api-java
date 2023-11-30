package dev.eliezer.superticket.domain.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "tb_users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String cpf;

    private String cep;
    private String address;
    private String addressNumber;
    private String state;
    private String city;

}