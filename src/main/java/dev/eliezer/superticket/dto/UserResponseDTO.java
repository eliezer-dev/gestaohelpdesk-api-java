package dev.eliezer.superticket.dto;

import dev.eliezer.superticket.domain.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDTO {

    private Long id;
    @Schema(example = "Paulo Silva", requiredMode = Schema.RequiredMode.REQUIRED, description = "name and last name of user")
    private String name;
    @Schema(example = "12345678901", requiredMode = Schema.RequiredMode.REQUIRED, description = "cpf of user")
    private String cpf;
    @Schema(example = "12345678", requiredMode = Schema.RequiredMode.REQUIRED, description = "zip code of user")
    private String cep;
    @Schema(example = "Rua São Paulo", requiredMode = Schema.RequiredMode.REQUIRED, description = "address of user")
    private String address;
    @Schema(example = "123", requiredMode = Schema.RequiredMode.REQUIRED, description = "addressNumber of user")
    private String addressNumber;
    @Schema(example = "SP", requiredMode = Schema.RequiredMode.REQUIRED, description = "state of user")
    private String state;
    @Schema(example = "Sao Paulo", requiredMode = Schema.RequiredMode.REQUIRED, description = "city of user")
    private String city;
    @Schema(example = "paulo@gmail.com", requiredMode = Schema.RequiredMode.REQUIRED, description = "email of user")
    private String email;
    private LocalDateTime createAt;


}
