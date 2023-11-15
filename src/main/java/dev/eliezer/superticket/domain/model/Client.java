package dev.eliezer.superticket.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "tb_clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String razaoSocialOrName;
    String cpfOrCnpj;
    String cep;
    String address;
    String addressNumber;
    String state;
    String city;
}
