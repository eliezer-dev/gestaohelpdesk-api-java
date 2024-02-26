package dev.eliezer.superticket.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthUserResponseDTO {
    @Schema(example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJTdXBlclRpY2tldCIsInN1YiI6IjIiLCJyb2xlcyI6WyJ1c2VyIl0sImV4cCI6MTcwMjY2NjM2OX0.TZD8tFG4CmnaBc8baO4r8L_TG2fD0Ph8z7zB8q3xwMA")
    private String access_token;
    @Schema(example = "1702666369728")
    private Long expires_in;
    private UserForAuthResponseDTO user;
}
