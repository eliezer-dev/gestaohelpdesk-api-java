package dev.eliezer.superticket.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "tb_clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String cpfOrCnpj;
    String razaoSocialOrName;
    String cep;
    String address;
    String addressNumber;
    String state;
    String city;

}
