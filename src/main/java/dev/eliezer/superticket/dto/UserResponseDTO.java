package dev.eliezer.superticket.dto;

import dev.eliezer.superticket.domain.model.User;
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
    private String name;
    private String cpf;

    private String cep;

    private String address;

    private String addressNumber;

    private String state;

    private String city;

    private String email;

    private LocalDateTime createAt;


}
