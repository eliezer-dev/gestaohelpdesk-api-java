package dev.eliezer.superticket.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity(name = "tb_users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "[name] is not provided.")
    private String name;

    @NotBlank(message = "[cpf] is not provided.")
    @Pattern(regexp = "\\d{11}", message = "[cpf] needs 11 digits and cannot contain a dot or other special characters.")
    private String cpf;
    @Pattern(regexp = "\\d{8}", message = "[cep] needs 11 digits and cannot contain a dash or other special characters.")
    @NotBlank(message = "[cep] is not provided.")
    private String cep;
    @NotBlank(message = "[address] is not provided.")
    private String address;
    @NotBlank(message = "[address number] is not provided.")
    private String addressNumber;
    @NotBlank(message = "[state] is not provided.")
    private String state;
    @NotBlank(message = "[city] is not provided.")
    private String city;

}