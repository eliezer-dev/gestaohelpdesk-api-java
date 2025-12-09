package dev.eliezer.gestaohelpdesk.modules.user.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public record UserRequestDTO(

   
    @NotBlank(message = "[name] is not provided.")
    @Schema(example = "Paulo Silva", requiredMode = Schema.RequiredMode.REQUIRED, description = "name and last name of user")
    String name,

    @NotBlank(message = "[cpf] is not provided.")
    @Pattern(regexp = "\\d{11}", message = "[cpf] needs 11 digits and cannot contain a dot or other special characters.")
    @Schema(example = "12345678901", requiredMode = Schema.RequiredMode.REQUIRED, description = "cpf of user")
    String cpf,

    @Pattern(regexp = "\\d{8}", message = "[cep] needs 11 digits and cannot contain a dash or other special characters.")
    @NotBlank(message = "[cep] is not provided.")
    @Schema(example = "12345678", requiredMode = Schema.RequiredMode.REQUIRED, description = "zip code of user")
    String cep,

    @NotBlank(message = "[address] is not provided.")
    @Schema(example = "Avenida Paulista", requiredMode = Schema.RequiredMode.REQUIRED, description = "address of user")
    String address,

    @NotBlank(message = "[address number] is not provided.")
    @Schema(example = "123", requiredMode = Schema.RequiredMode.REQUIRED, description = "address number of user")
    String addressNumber,

    @Schema(example = "Proximo a Praça das Bandeiras", description = "address number 2 of client ")
    String addressNumber2,

    @NotBlank(message = "[state] is not provided.")
    @Schema(example = "SP", requiredMode = Schema.RequiredMode.REQUIRED, description = "state of user")
    String state,    

    @NotBlank(message = "[city] is not provided.")
    @Schema(example = "Sao Paulo", requiredMode = Schema.RequiredMode.REQUIRED, description = "city of user")
    String city,

    @NotBlank(message = "[neighborhood] is not provided.")
    @Schema(example = "Cidade Jardim", requiredMode = Schema.RequiredMode.REQUIRED, description = "neighborhood of client")
    String neighborhood,

    @Email(message = "[email] is invalid.")
    @Schema(example = "paulo@gmail.com", requiredMode = Schema.RequiredMode.REQUIRED, description = "email of user")
    String email,

    @Length(min = 8, max = 100, message = "[password] length must be between 8 and 100 characters")
    @Schema(example = "senha@1234", requiredMode = Schema.RequiredMode.REQUIRED, description = "password of user", minLength = 8, maxLength = 30)
    String password,


    @NotNull(message = "[userRole] is not provided.")
    @Schema(example = "1", requiredMode = Schema.RequiredMode.REQUIRED, description =
            """
            1 - support representative.
            2 - manager.
            3 - user test (read only)
            """)
    Long userRole
    
){}

