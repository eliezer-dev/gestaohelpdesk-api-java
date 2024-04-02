package dev.eliezer.superticket.domain.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Entity(name = "tb_clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(example = "1",
            requiredMode = Schema.RequiredMode.REQUIRED, description = "id of client")
    Long id;

    @NotBlank(message = "[cpf_Cnpj] is not provided.")
    @Pattern(regexp = "^(\\d{11}|\\d{14})$", message = "[cpfCnpj] needs 11 or 14 digits and cannot contain dot or other special character.")
    @Schema(example = "98765432109876", requiredMode = Schema.RequiredMode.REQUIRED, description = "CPF or CNPJ of user")
    String cpfCnpj;

    @NotBlank(message = "[razaoSocialName] is not provided.")
    @Schema(example = "Emporio Juca e Rosa Me", requiredMode = Schema.RequiredMode.REQUIRED, description = "company name or name of client")
    String razaoSocialName;

    @Schema(example = "Emporio do Juca", requiredMode = Schema.RequiredMode.REQUIRED, description = "business name or first name of client.")
    String businessName;

    @Pattern(regexp = "\\d{8}", message = "[cep] needs 11 digits and cannot contain a dash or other special characters.")
    @Schema(example = "87654321", requiredMode = Schema.RequiredMode.REQUIRED, description = "zip code of client")
    String cep;

    @NotBlank(message = "[address] is not provided.")
    @Schema(example = "Rua Rio de Janeiro", requiredMode = Schema.RequiredMode.REQUIRED, description = "address of client")
    String address;

    @NotBlank(message = "[addressNumber] is not provided.")
    @Schema(example = "456", requiredMode = Schema.RequiredMode.REQUIRED, description = "address number of client")
    String addressNumber;

    @NotBlank(message = "[state] is not provided.")
    @Schema(example = "RJ", requiredMode = Schema.RequiredMode.REQUIRED, description = "state of client")
    String state;

    @NotBlank(message = "[city] is not provided.")
    @Schema(example = "Rio de Janeiro", requiredMode = Schema.RequiredMode.REQUIRED, description = "city of client")
    String city;

    @Email(message = "[email] is invalid.")
    @Schema(example = "contato@emporiojucarosa.com.br", description = "email of client")
    String email;

    @CreationTimestamp
    private LocalDateTime createAt;

    @UpdateTimestamp
    private LocalDateTime updateAt;

}
