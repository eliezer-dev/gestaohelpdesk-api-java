package dev.eliezer.superticket.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserForUpdateRequestDTO {
    @Schema(example = "1", description = "user id")
    private Long id;

    @Schema(example = "Jose Carlos", description = "name of user")
    private String name;

    @Schema(example = "12345678901", description = "cpf of user")
    private String cpf;

    @Schema(example = "12345678", description = "zip code of user")
    private String cep;

    @Schema(example = "Avenida Paulista", description = "address of user")
    private String address;

    @Schema(example = "123", description = "address number of user")
    private String addressNumber;

    @Schema(example = "SP", description = "state of user")
    private String state;

    @Schema(example = "Sao Paulo", description = "city of user")
    private String city;

    @Schema(example = "paulo@gmail.com", description = "email of user")
    private String email;

    @Schema(example = "password12345", description = "current password")
    private String oldPassword;

    @Schema(example = "12345password", description = "new password")
    private String newPassword;

}
