package dev.eliezer.superticket.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class StatusForTicketRequestDTO {
    @Schema(example = "1", requiredMode = Schema.RequiredMode.REQUIRED, description = "id of status")
    private Long id;
}
