package dev.eliezer.superticket.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity(name = "tb_clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @NotBlank(message = "[cpfOrCnpj] is not provided.")
    @Pattern(regexp = "^(\\d{11}|\\d{14})$", message = "[cpfOrCnpj] needs 11 or 14 digits and cannot contain dot or other special character.")
    String cpfOrCnpj;
    @NotBlank(message = "[razaoSocialOrName] is not provided.")
    String razaoSocialOrName;
    @Pattern(regexp = "\\d{8}", message = "[cep] needs 11 digits and cannot contain a dash or other special characters.")
    String cep;
    @NotBlank(message = "[address] is not provided.")
    String address;
    @NotBlank(message = "[addressNumber] is not provided.")
    String addressNumber;
    @NotBlank(message = "[state] is not provided.")
    String state;
    @NotBlank(message = "[city] is not provided.")
    String city;

}
