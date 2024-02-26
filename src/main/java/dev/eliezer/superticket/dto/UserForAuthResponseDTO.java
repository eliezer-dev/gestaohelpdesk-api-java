package dev.eliezer.superticket.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserForAuthResponseDTO {
    @Schema(example = "1")
    private Long id;
    @Schema(example = "Jose Carlos")
    private String name;
}
