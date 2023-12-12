package dev.eliezer.superticket.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity(name = "tb_clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @NotBlank(message = "[cpf_Cnpj] is not provided.")
    @Pattern(regexp = "^(\\d{11}|\\d{14})$", message = "[cpfCnpj] needs 11 or 14 digits and cannot contain dot or other special character.")
    String cpfCnpj;
    @NotBlank(message = "[razaoSocialName] is not provided.")
    String razaoSocialName;
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
    @Email(message = "[email] is invalid.")
    String email;
    @CreationTimestamp
    private LocalDateTime createAt;

}
