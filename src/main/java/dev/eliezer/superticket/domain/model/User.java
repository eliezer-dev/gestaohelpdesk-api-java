package dev.eliezer.superticket.domain.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Data
@Entity(name = "tb_users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "[name] is not provided.")
    @Schema(example = "Paulo Silva", requiredMode = Schema.RequiredMode.REQUIRED, description = "name and last name of user")
    private String name;

    @NotBlank(message = "[cpf] is not provided.")
    @Pattern(regexp = "\\d{11}", message = "[cpf] needs 11 digits and cannot contain a dot or other special characters.")
    @Schema(example = "12345678901", requiredMode = Schema.RequiredMode.REQUIRED, description = "cpf of user")
    private String cpf;
    @Pattern(regexp = "\\d{8}", message = "[cep] needs 11 digits and cannot contain a dash or other special characters.")
    @NotBlank(message = "[cep] is not provided.")
    @Schema(example = "12345678", requiredMode = Schema.RequiredMode.REQUIRED, description = "zip code of user")
    private String cep;
    @NotBlank(message = "[address] is not provided.")
    @Schema(example = "Rua São Paulo", requiredMode = Schema.RequiredMode.REQUIRED, description = "address of user")
    private String address;
    @NotBlank(message = "[address number] is not provided.")
    @Schema(example = "123", requiredMode = Schema.RequiredMode.REQUIRED, description = "addressNumber of user")
    private String addressNumber;
    @NotBlank(message = "[state] is not provided.")
    @Schema(example = "SP", requiredMode = Schema.RequiredMode.REQUIRED, description = "state of user")
    private String state;
    @NotBlank(message = "[city] is not provided.")
    @Schema(example = "Sao Paulo", requiredMode = Schema.RequiredMode.REQUIRED, description = "city of user")
    private String city;
    @Email(message = "[email] is invalid.")
    @Schema(example = "paulo@gmail.com", requiredMode = Schema.RequiredMode.REQUIRED, description = "email of user")
    private String email;
    @Length(min = 8, max = 100, message = "[password] length must be between 8 and 100 characters")
    @Schema(example = "senha@1234", requiredMode = Schema.RequiredMode.REQUIRED, description = "password of user", minLength = 8, maxLength = 30)
    private String password;
    @CreationTimestamp
    private LocalDateTime createAt;


}