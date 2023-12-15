package dev.eliezer.superticket.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthUserRequestDTO {
    @Schema(example = "paulo@gmail.com", requiredMode = Schema.RequiredMode.REQUIRED, description = "email of user")
    private String email;
    @Schema(example = "senha@1234", requiredMode = Schema.RequiredMode.REQUIRED, description = "password of user", minLength = 8, maxLength = 30)
    private String password;
}
