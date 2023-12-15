package dev.eliezer.superticket.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserForTicketResponseDTO {
    private Long id;
    @Schema(example = "Paulo Silva", requiredMode = Schema.RequiredMode.REQUIRED, description = "name and last name of user")
    private String name;
}
